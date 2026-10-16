package com.linguaquest.di

import android.content.Context
import com.linguaquest.data.local.LinguaQuestDatabase
import com.linguaquest.data.repository.ContentRepository
import com.linguaquest.data.repository.CurriculumRepository
import com.linguaquest.data.repository.LessonRepository
import com.linguaquest.data.repository.ProgressRepository
import com.linguaquest.data.repository.UserRepository
import com.linguaquest.data.repository.SpacedRepetitionRepository
import com.linguaquest.data.repository.StreakRepository

class AppContainer(context: Context) {

    private val database = LinguaQuestDatabase.build(context.applicationContext)

    private val contentDao = database.contentDao()
    private val progressDao = database.progressDao()
    private val userDao = database.userDao()

    val contentRepository = ContentRepository(contentDao)
    val progressRepository = ProgressRepository(progressDao)
    val userRepository = UserRepository(userDao)
    val curriculumRepository = CurriculumRepository(contentDao, progressDao)
    val spacedRepetitionRepository = SpacedRepetitionRepository(progressDao)
    val streakRepository = StreakRepository(progressDao)
    val lessonRepository = LessonRepository(contentDao, progressDao)
}