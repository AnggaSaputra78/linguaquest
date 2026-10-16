package com.linguaquest.data.repository

import com.linguaquest.data.local.ContentDao
import com.linguaquest.data.local.ProgressDao
import com.linguaquest.data.local.entity.LessonEntity
import com.linguaquest.data.local.entity.LessonProgressEntity
import com.linguaquest.data.local.entity.UnitEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class UnitWithLessonsUi(
    val unit: UnitEntity,
    val lessons: List<LessonEntity>
)

class CurriculumRepository(
    private val contentDao: ContentDao,
    private val progressDao: ProgressDao
) {

    private val mainCourseId = "english-foundations"

    suspend fun getCurriculum(): List<UnitWithLessonsUi> {
        val course = contentDao.getCourseById(mainCourseId) ?: return emptyList()
        val units = contentDao.getUnits(course.id)

        return units.map { unit ->
            UnitWithLessonsUi(
                unit = unit,
                lessons = contentDao.getLessons(unit.id)
            )
        }
    }

    fun observeLessonProgressMap(): Flow<Map<String, LessonProgressEntity>> {
        return progressDao.observeAllLessonProgress()
            .map { progressList ->
                progressList.associateBy { it.lessonId }
            }
    }
}