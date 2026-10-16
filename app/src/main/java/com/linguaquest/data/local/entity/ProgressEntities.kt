package com.linguaquest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val id: Int = 1,

    @ColumnInfo(name = "display_name")
    val displayName: String = "Pembelajar",

    @ColumnInfo(name = "target_minutes_per_day")
    val targetMinutesPerDay: Int = 30,

    @ColumnInfo(name = "onboarding_completed")
    val onboardingCompleted: Boolean = false,

    @ColumnInfo(name = "placement_completed")
    val placementCompleted: Boolean = false,

    @ColumnInfo(name = "current_level_id")
    val currentLevelId: String? = null,

    @ColumnInfo(name = "created_at_epoch_millis")
    val createdAtEpochMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "learning_sessions")
data class LearningSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "started_at_epoch_millis")
    val startedAtEpochMillis: Long,

    @ColumnInfo(name = "ended_at_epoch_millis")
    val endedAtEpochMillis: Long? = null,

    @ColumnInfo(name = "focus_skill")
    val focusSkill: String? = null,

    @ColumnInfo(name = "xp_earned")
    val xpEarned: Int = 0
)

@Entity(
    tableName = "answer_attempts",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("exercise_id")
    ]
)
data class AnswerAttemptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "exercise_id")
    val exerciseId: String,

    @ColumnInfo(name = "lesson_id")
    val lessonId: String? = null,

    @ColumnInfo(name = "is_correct")
    val isCorrect: Boolean,

    @ColumnInfo(name = "answer_text")
    val answerText: String? = null,

    @ColumnInfo(name = "created_at_epoch_millis")
    val createdAtEpochMillis: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "lesson_progress",
    foreignKeys = [
        ForeignKey(
            entity = LessonEntity::class,
            parentColumns = ["id"],
            childColumns = ["lesson_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LessonProgressEntity(
    @PrimaryKey
    @ColumnInfo(name = "lesson_id")
    val lessonId: String,

    @ColumnInfo(name = "status")
    val status: String = "NOT_STARTED",

    @ColumnInfo(name = "completed_at_epoch_millis")
    val completedAtEpochMillis: Long? = null,

    @ColumnInfo(name = "mastery_score")
    val masteryScore: Int? = null,

    @ColumnInfo(name = "last_attempt_at_epoch_millis")
    val lastAttemptAtEpochMillis: Long? = null
)

@Entity(
    tableName = "vocabulary_reviews",
    foreignKeys = [
        ForeignKey(
            entity = VocabularyEntity::class,
            parentColumns = ["id"],
            childColumns = ["vocabulary_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class VocabularyReviewEntity(
    @PrimaryKey
    @ColumnInfo(name = "vocabulary_id")
    val vocabularyId: String,

    @ColumnInfo(name = "last_review_epoch_millis")
    val lastReviewEpochMillis: Long? = null,

    @ColumnInfo(name = "review_count")
    val reviewCount: Int = 0,

    @ColumnInfo(name = "error_count")
    val errorCount: Int = 0,

    @ColumnInfo(name = "mastery_level")
    val masteryLevel: Int = 0,

    @ColumnInfo(name = "next_review_epoch_millis")
    val nextReviewEpochMillis: Long? = null,

    @ColumnInfo(name = "difficulty")
    val difficulty: Int = 1
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "unlocked_at_epoch_millis")
    val unlockedAtEpochMillis: Long? = null
)

@Entity(tableName = "daily_goal")
data class DailyGoalEntity(
    @PrimaryKey
    val id: Int = 1,

    @ColumnInfo(name = "target_minutes")
    val targetMinutes: Int = 30,

    @ColumnInfo(name = "target_xp")
    val targetXp: Int = 50,

    @ColumnInfo(name = "updated_at_epoch_millis")
    val updatedAtEpochMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "study_reminder")
data class StudyReminderEntity(
    @PrimaryKey
    val id: Int = 1,

    @ColumnInfo(name = "enabled")
    val enabled: Boolean = false,

    @ColumnInfo(name = "hour")
    val hour: Int = 19,

    @ColumnInfo(name = "minute")
    val minute: Int = 0,

    @ColumnInfo(name = "days_of_week_mask")
    val daysOfWeekMask: Int = 127
)

@Entity(tableName = "personal_notes")
data class PersonalNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "lesson_id")
    val lessonId: String? = null,

    @ColumnInfo(name = "vocabulary_id")
    val vocabularyId: String? = null,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "updated_at_epoch_millis")
    val updatedAtEpochMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "lesson_id")
    val lessonId: String? = null,

    @ColumnInfo(name = "unit_id")
    val unitId: String? = null,

    @ColumnInfo(name = "created_at_epoch_millis")
    val createdAtEpochMillis: Long = System.currentTimeMillis()
)
@Entity(tableName = "daily_activity")
data class DailyActivityEntity(
    @PrimaryKey
    @ColumnInfo(name = "date_string")
    val dateString: String, // Format: YYYY-MM-DD

    @ColumnInfo(name = "xp_earned")
    val xpEarned: Int = 0,

    @ColumnInfo(name = "lessons_completed")
    val lessonsCompleted: Int = 0,

    @ColumnInfo(name = "minutes_studied")
    val minutesStudied: Int = 0,

    @ColumnInfo(name = "updated_at_epoch_millis")
    val updatedAtEpochMillis: Long = System.currentTimeMillis()
)