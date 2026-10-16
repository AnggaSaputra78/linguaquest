package com.linguaquest.data.repository

import com.linguaquest.data.local.ProgressDao
import com.linguaquest.data.local.entity.VocabularyEntity
import com.linguaquest.data.local.entity.VocabularyReviewEntity
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class SpacedRepetitionRepository(
    private val progressDao: ProgressDao
) {
    private val intervals = listOf(
        TimeUnit.DAYS.toMillis(1),
        TimeUnit.DAYS.toMillis(3),
        TimeUnit.DAYS.toMillis(7),
        TimeUnit.DAYS.toMillis(14),
        TimeUnit.DAYS.toMillis(30)
    )

    suspend fun getDueVocabulary(limit: Int = 20): List<VocabularyEntity> {
        return progressDao.getDueVocabulary(System.currentTimeMillis(), limit)
    }

    fun observeDueCount(): Flow<Int> {
        return progressDao.observeDueVocabularyCount(System.currentTimeMillis())
    }

    suspend fun recordReview(vocabId: String, isCorrect: Boolean) {
        val now = System.currentTimeMillis()
        val existing = progressDao.getVocabularyReview(vocabId)

        val newMastery: Int
        val newDifficulty: Int
        val nextInterval: Long

        if (existing == null) {
            newMastery = if (isCorrect) 1 else 0
            newDifficulty = if (isCorrect) 1 else 2
            nextInterval = if (isCorrect) intervals[0] else TimeUnit.HOURS.toMillis(1)
        } else {
            if (isCorrect) {
                newMastery = (existing.masteryLevel + 1).coerceAtMost(intervals.size)
                newDifficulty = (existing.difficulty - 1).coerceAtLeast(1)
                val intervalIndex = (newMastery - 1).coerceIn(0, intervals.size - 1)
                nextInterval = intervals[intervalIndex]
            } else {
                newMastery = 0
                newDifficulty = (existing.difficulty + 1).coerceAtMost(5)
                nextInterval = TimeUnit.HOURS.toMillis(4)
            }
        }

        val review = VocabularyReviewEntity(
            vocabularyId = vocabId,
            lastReviewEpochMillis = now,
            reviewCount = (existing?.reviewCount ?: 0) + 1,
            errorCount = (existing?.errorCount ?: 0) + if (!isCorrect) 1 else 0,
            masteryLevel = newMastery,
            nextReviewEpochMillis = now + nextInterval,
            difficulty = newDifficulty
        )

        progressDao.upsertVocabularyReview(review)
    }
}