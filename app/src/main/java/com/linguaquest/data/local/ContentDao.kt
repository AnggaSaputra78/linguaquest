package com.linguaquest.data.local

import androidx.room.Dao
import androidx.room.Query
import com.linguaquest.data.local.entity.CourseEntity
import com.linguaquest.data.local.entity.ExerciseEntity
import com.linguaquest.data.local.entity.ExerciseOptionEntity
import com.linguaquest.data.local.entity.LessonEntity
import com.linguaquest.data.local.entity.UnitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {

    @Query("SELECT COUNT(*) FROM courses")
    fun observeCourseCount(): Flow<Int>

    @Query("SELECT * FROM courses ORDER BY display_order")
    suspend fun getCourses(): List<CourseEntity>

    @Query("SELECT * FROM courses ORDER BY display_order LIMIT 1")
    suspend fun getFirstCourse(): CourseEntity?

    @Query("SELECT * FROM courses WHERE id = :courseId")
    suspend fun getCourseById(courseId: String): CourseEntity?

    @Query("SELECT * FROM units WHERE course_id = :courseId ORDER BY display_order")
    suspend fun getUnits(courseId: String): List<UnitEntity>

    @Query("SELECT * FROM units WHERE course_id = :courseId ORDER BY display_order LIMIT 1")
    suspend fun getFirstUnit(courseId: String): UnitEntity?

    @Query("SELECT * FROM lessons WHERE unit_id = :unitId ORDER BY display_order")
    suspend fun getLessons(unitId: String): List<LessonEntity>

    @Query("SELECT * FROM lessons WHERE unit_id = :unitId ORDER BY display_order LIMIT 1")
    suspend fun getFirstLesson(unitId: String): LessonEntity?

    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    suspend fun getLessonById(lessonId: String): LessonEntity?

    @Query("SELECT * FROM exercises WHERE lesson_id = :lessonId ORDER BY display_order")
    suspend fun getExercises(lessonId: String): List<ExerciseEntity>

    @Query("SELECT * FROM exercise_options WHERE exercise_id = :exerciseId ORDER BY display_order")
    suspend fun getExerciseOptions(exerciseId: String): List<ExerciseOptionEntity>
}