package com.linguaquest.data.repository

import com.linguaquest.data.local.ContentDao
import com.linguaquest.data.local.ProgressDao
import com.linguaquest.data.local.entity.AnswerAttemptEntity
import com.linguaquest.data.local.entity.LessonEntity
import com.linguaquest.data.local.entity.LessonProgressEntity
import com.linguaquest.data.model.ExerciseOptionUi
import com.linguaquest.data.model.ExerciseUi
import kotlinx.coroutines.flow.Flow

class LessonRepository(
    private val contentDao: ContentDao,
    private val progressDao: ProgressDao
) {

    suspend fun getLesson(lessonId: String): LessonEntity? {
        return contentDao.getLessonById(lessonId)
    }

    suspend fun getExercises(lessonId: String): List<ExerciseUi> {
        val exercises = contentDao.getExercises(lessonId)

        return exercises.map { exercise ->
            val options = contentDao.getExerciseOptions(exercise.id)

            ExerciseUi(
                id = exercise.id,
                lessonId = exercise.lessonId,
                type = exercise.type,
                skill = exercise.skill,
                instruction = exercise.instruction,
                prompt = exercise.prompt,
                answer = exercise.answer,
                explanation = exercise.explanation,
                difficulty = exercise.difficulty,
                options = options.map { option ->
                    ExerciseOptionUi(
                        id = option.id,
                        text = option.text,
                        isCorrect = option.isCorrect
                    )
                }
            )
        }
    }

    suspend fun saveAttempt(
        exerciseId: String,
        lessonId: String,
        isCorrect: Boolean,
        answerText: String?
    ) {
        progressDao.insertAnswerAttempt(
            AnswerAttemptEntity(
                exerciseId = exerciseId,
                lessonId = lessonId,
                isCorrect = isCorrect,
                answerText = answerText
            )
        )
    }

    suspend fun completeLesson(
        lessonId: String,
        masteryScore: Int
    ) {
        progressDao.upsertLessonProgress(
            LessonProgressEntity(
                lessonId = lessonId,
                status = "COMPLETED",
                completedAtEpochMillis = System.currentTimeMillis(),
                masteryScore = masteryScore,
                lastAttemptAtEpochMillis = System.currentTimeMillis()
            )
        )
    }

    fun observeLessonProgress(lessonId: String): Flow<LessonProgressEntity?> {
        return progressDao.observeLessonProgress(lessonId)
    }
}