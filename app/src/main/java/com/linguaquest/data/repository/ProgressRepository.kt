package com.linguaquest.data.repository

import com.linguaquest.data.local.ProgressDao
import kotlinx.coroutines.flow.Flow

class ProgressRepository(
    private val progressDao: ProgressDao
) {

    fun observeTotalXp(): Flow<Int> {
        return progressDao.observeTotalXp()
    }
}