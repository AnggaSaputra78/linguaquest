package com.linguaquest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.linguaquest.data.local.entity.AchievementEntity
import com.linguaquest.data.local.entity.AnswerAttemptEntity
import com.linguaquest.data.local.entity.BookmarkEntity
import com.linguaquest.data.local.entity.CourseEntity
import com.linguaquest.data.local.entity.DailyActivityEntity
import com.linguaquest.data.local.entity.DailyGoalEntity
import com.linguaquest.data.local.entity.ExampleSentenceEntity
import com.linguaquest.data.local.entity.ExerciseEntity
import com.linguaquest.data.local.entity.ExerciseOptionEntity
import com.linguaquest.data.local.entity.LearningLevelEntity
import com.linguaquest.data.local.entity.LearningSessionEntity
import com.linguaquest.data.local.entity.LessonEntity
import com.linguaquest.data.local.entity.LessonProgressEntity
import com.linguaquest.data.local.entity.PersonalNoteEntity
import com.linguaquest.data.local.entity.StudyReminderEntity
import com.linguaquest.data.local.entity.UnitEntity
import com.linguaquest.data.local.entity.UserProfileEntity
import com.linguaquest.data.local.entity.VocabularyEntity
import com.linguaquest.data.local.entity.VocabularyReviewEntity

@Database(
    entities = [
        LearningLevelEntity::class,
        CourseEntity::class,
        UnitEntity::class,
        LessonEntity::class,
        ExerciseEntity::class,
        ExerciseOptionEntity::class,
        VocabularyEntity::class,
        ExampleSentenceEntity::class,
        UserProfileEntity::class,
        LearningSessionEntity::class,
        AnswerAttemptEntity::class,
        LessonProgressEntity::class,
        VocabularyReviewEntity::class,
        AchievementEntity::class,
        DailyGoalEntity::class,
        StudyReminderEntity::class,
        PersonalNoteEntity::class,
        BookmarkEntity::class,
        DailyActivityEntity::class
    ],
    version = 4,
    exportSchema = true
)
abstract class LinguaQuestDatabase : RoomDatabase() {

    abstract fun contentDao(): ContentDao

    abstract fun progressDao(): ProgressDao

    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "linguaquest.db"

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE user_profile ADD COLUMN current_level_id TEXT"
                )
                SeedData.seedPlacement(database)
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                SeedData.seedAdditionalContent(database)
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `daily_activity` (
                        `date_string` TEXT NOT NULL, 
                        `xp_earned` INTEGER NOT NULL DEFAULT 0, 
                        `lessons_completed` INTEGER NOT NULL DEFAULT 0, 
                        `minutes_studied` INTEGER NOT NULL DEFAULT 0, 
                        `updated_at_epoch_millis` INTEGER NOT NULL DEFAULT 0, 
                        PRIMARY KEY(`date_string`)
                    )
                    """.trimIndent()
                )
            }
        }

        fun build(context: Context): LinguaQuestDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                LinguaQuestDatabase::class.java,
                DATABASE_NAME
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                .addCallback(SeedCallback())
                .build()
        }
    }

    private class SeedCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            SeedData.seed(db)
        }
    }
}