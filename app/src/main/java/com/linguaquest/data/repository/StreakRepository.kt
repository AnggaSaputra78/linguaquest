package com.linguaquest.data.repository

import com.linguaquest.data.local.ProgressDao
import com.linguaquest.data.local.entity.DailyActivityEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StreakRepository(
    private val progressDao: ProgressDao
) {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    suspend fun recordActivity(xpEarned: Int, minutesStudied: Int) {
        if (xpEarned <= 0 && minutesStudied <= 0) return

        val today = LocalDate.now().format(formatter)
        val existing = progressDao.getDailyActivity(today)

        val activity = DailyActivityEntity(
            dateString = today,
            xpEarned = (existing?.xpEarned ?: 0) + xpEarned,
            lessonsCompleted = existing?.lessonsCompleted ?: 0,
            minutesStudied = (existing?.minutesStudied ?: 0) + minutesStudied,
            updatedAtEpochMillis = System.currentTimeMillis()
        )

        progressDao.upsertDailyActivity(activity)
    }

    suspend fun recordLessonCompleted() {
        val today = LocalDate.now().format(formatter)
        val existing = progressDao.getDailyActivity(today)

        val activity = DailyActivityEntity(
            dateString = today,
            xpEarned = existing?.xpEarned ?: 0,
            lessonsCompleted = (existing?.lessonsCompleted ?: 0) + 1,
            minutesStudied = existing?.minutesStudied ?: 0,
            updatedAtEpochMillis = System.currentTimeMillis()
        )

        progressDao.upsertDailyActivity(activity)
    }

    suspend fun getCurrentStreak(): Int {
        val activities = progressDao.getRecentActivities()
        if (activities.isEmpty()) return 0

        var streak = 0
        var expectedDate = LocalDate.now()

        // Cek apakah hari ini sudah ada aktivitas
        val todayActivity = activities.firstOrNull { it.dateString == expectedDate.format(formatter) }
        if (todayActivity == null) {
            // Jika hari ini belum ada aktivitas, cek kemarin (grace period 1 hari)
            expectedDate = expectedDate.minusDays(1)
        }

        for (activity in activities) {
            if (activity.dateString == expectedDate.format(formatter)) {
                streak++
                expectedDate = expectedDate.minusDays(1)
            } else {
                break
            }
        }

        return streak
    }
}