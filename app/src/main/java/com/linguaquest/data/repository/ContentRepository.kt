package com.linguaquest.data.repository

import com.linguaquest.data.local.ContentDao
import kotlinx.coroutines.flow.Flow

class ContentRepository(
    private val contentDao: ContentDao
) {

    private val mainCourseId = "english-foundations"

    fun observeCourseCount(): Flow<Int> {
        return contentDao.observeCourseCount()
    }

    suspend fun getFirstLessonRoute(): String? {
        val course = contentDao.getCourseById(mainCourseId) ?: return null
        val unit = contentDao.getFirstUnit(course.id) ?: return null
        val lesson = contentDao.getFirstLesson(unit.id) ?: return null
        return lesson.id
    }
}