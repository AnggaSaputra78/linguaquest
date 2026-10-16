package com.linguaquest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "learning_levels")
data class LearningLevelEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "display_order")
    val displayOrder: Int
)

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "display_order")
    val displayOrder: Int
)

@Entity(
    tableName = "units",
    foreignKeys = [
        ForeignKey(
            entity = CourseEntity::class,
            parentColumns = ["id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LearningLevelEntity::class,
            parentColumns = ["id"],
            childColumns = ["level_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("course_id"),
        Index("level_id")
    ]
)
data class UnitEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "course_id")
    val courseId: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "level_id")
    val levelId: String,

    @ColumnInfo(name = "display_order")
    val displayOrder: Int
)

@Entity(
    tableName = "lessons",
    foreignKeys = [
        ForeignKey(
            entity = UnitEntity::class,
            parentColumns = ["id"],
            childColumns = ["unit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("unit_id")
    ]
)
data class LessonEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "unit_id")
    val unitId: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "estimated_minutes")
    val estimatedMinutes: Int,

    @ColumnInfo(name = "display_order")
    val displayOrder: Int
)

@Entity(
    tableName = "exercises",
    foreignKeys = [
        ForeignKey(
            entity = LessonEntity::class,
            parentColumns = ["id"],
            childColumns = ["lesson_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("lesson_id")
    ]
)
data class ExerciseEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "lesson_id")
    val lessonId: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "skill")
    val skill: String,

    @ColumnInfo(name = "instruction")
    val instruction: String,

    @ColumnInfo(name = "prompt")
    val prompt: String,

    @ColumnInfo(name = "answer")
    val answer: String,

    @ColumnInfo(name = "explanation")
    val explanation: String,

    @ColumnInfo(name = "difficulty")
    val difficulty: Int,

    @ColumnInfo(name = "display_order")
    val displayOrder: Int
)

@Entity(
    tableName = "exercise_options",
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
data class ExerciseOptionEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "exercise_id")
    val exerciseId: String,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "is_correct")
    val isCorrect: Boolean,

    @ColumnInfo(name = "display_order")
    val displayOrder: Int
)

@Entity(
    tableName = "vocabulary",
    foreignKeys = [
        ForeignKey(
            entity = LessonEntity::class,
            parentColumns = ["id"],
            childColumns = ["lesson_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("lesson_id")
    ]
)
data class VocabularyEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "lesson_id")
    val lessonId: String,

    @ColumnInfo(name = "english")
    val english: String,

    @ColumnInfo(name = "indonesian")
    val indonesian: String,

    @ColumnInfo(name = "pronunciation")
    val pronunciation: String,

    @ColumnInfo(name = "example_en")
    val exampleEn: String,

    @ColumnInfo(name = "example_id")
    val exampleId: String,

    @ColumnInfo(name = "difficulty")
    val difficulty: Int
)

@Entity(
    tableName = "example_sentences",
    foreignKeys = [
        ForeignKey(
            entity = VocabularyEntity::class,
            parentColumns = ["id"],
            childColumns = ["vocabulary_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("vocabulary_id")
    ]
)
data class ExampleSentenceEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "vocabulary_id")
    val vocabularyId: String,

    @ColumnInfo(name = "english")
    val english: String,

    @ColumnInfo(name = "indonesian")
    val indonesian: String
)