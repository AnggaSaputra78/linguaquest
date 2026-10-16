package com.linguaquest.data.repository

import com.linguaquest.data.local.UserDao
import com.linguaquest.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao
) {

    fun observeUserProfile(): Flow<UserProfileEntity?> {
        return userDao.observeUserProfile()
    }

    suspend fun completeOnboarding(
        displayName: String,
        targetMinutesPerDay: Int
    ) {
        val existing = userDao.getUserProfile()

        val profile = existing ?: UserProfileEntity()

        userDao.upsertUserProfile(
            profile.copy(
                displayName = displayName,
                targetMinutesPerDay = targetMinutesPerDay,
                onboardingCompleted = true,
                placementCompleted = false,
                currentLevelId = null
            )
        )
    }

    suspend fun completePlacement(levelId: String) {
        val existing = userDao.getUserProfile()

        val profile = existing ?: UserProfileEntity()

        userDao.upsertUserProfile(
            profile.copy(
                placementCompleted = true,
                currentLevelId = levelId
            )
        )
    }
}