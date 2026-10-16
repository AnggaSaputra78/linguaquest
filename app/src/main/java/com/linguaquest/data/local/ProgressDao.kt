package com.linguaquest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.linguaquest.data.local.entity.AnswerAttemptEntity
import com.linguaquest.data.local.entity.DailyActivityEntity
import com.linguaquest.data.local.entity.LessonProgressEntity
import com.linguaquest.data.local.entity.VocabularyEntity
import com.linguaquest.data.local.entity.VocabularyReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswerAttempt(attempt: AnswerAttemptEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLessonProgress(progress: LessonProgressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertVocabularyReview(review: VocabularyReviewEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDailyActivity(activity: DailyActivityEntity)

    @Query("SELECT * FROM lesson_progress")
    fun observeAllLessonProgress(): Flow<List<LessonProgressEntity>>

    @Query("SELECT * FROM lesson_progress WHERE lesson_id = :lessonId")
    fun observeLessonProgress(lessonId: String): Flow<LessonProgressEntity?>

    @Query(
        """
        SELECT 
            (SELECT COUNT(DISTINCT exercise_id) * 10 
             FROM answer_attempts 
             WHERE is_correct = 1)
        +
            (SELECT COUNT(*) * 20 
             FROM lesson_progress 
             WHERE status = 'COMPLETED')
        """
    )
    fun observeTotalXp(): Flow<Int>

    // SRS: Ambil kosakata yang butuh di-review (next_review <= sekarang)
    @Query(
        """
        SELECT v.* FROM vocabulary v
        INNER JOIN vocabulary_reviews vr ON v.id = vr.vocabulary_id
        WHERE vr.next_review_epoch_millis <= :currentTimeMillis
        ORDER BY vr.next_review_epoch_millis ASC
        LIMIT :limit
        """
    )
    suspend fun getDueVocabulary(currentTimeMillis: Long, limit: Int = 20): List<VocabularyEntity>

    @Query("SELECT COUNT(*) FROM vocabulary_reviews WHERE next_review_epoch_millis <= :currentTimeMillis")
    fun observeDueVocabularyCount(currentTimeMillis: Long): Flow<Int>

    @Query("SELECT * FROM vocabulary_reviews WHERE vocabulary_id = :vocabId")
    suspend fun getVocabularyReview(vocabId: String): VocabularyReviewEntity?

    // Streak: Ambil aktivitas harian diurutkan dari yang terbaru
    @Query("SELECT * FROM daily_activity WHERE xp_earned > 0 ORDER BY date_string DESC")
    suspend fun getRecentActivities(): List<DailyActivityEntity>

    @Query("SELECT * FROM daily_activity WHERE date_string = :dateString")
    suspend fun getDailyActivity(dateString: String): DailyActivityEntity?
}