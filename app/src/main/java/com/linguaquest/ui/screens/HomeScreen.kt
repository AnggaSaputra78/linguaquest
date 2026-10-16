package com.linguaquest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.linguaquest.ui.components.DailyQuestsCard
import com.linguaquest.ui.components.PowerUpsHUDStrip
import com.linguaquest.ui.components.SerpentineQuestPath
import com.linguaquest.ui.components.StickyPlayActionLauncher
import com.linguaquest.ui.components.TopGamificationHeader
import com.linguaquest.ui.components.UnitMissionBanner
import com.linguaquest.ui.home.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onContinueLearning: (String) -> Unit
) {
    val state by homeViewModel.uiState.collectAsState()

    val levelLabel = when (state.currentLevelId) {
        "A1" -> "A1"
        "A2" -> "A2"
        "B1_INTRO" -> "B1"
        "PRE_A1" -> "Pre-A1"
        else -> "Pre-A1"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header Atas dengan Streak dan XP (Menggunakan data asli dari ViewModel)
                TopGamificationHeader(
            streakDays = state.streakDays,
            gemsCount = "${state.totalXp} XP",
            currentLanguageFlag = "🇬🇧",
            currentLanguageCode = "EN · $levelLabel"
        )

        // Banner Misi Unit
        UnitMissionBanner(
            unitTag = "UNIT 1 • GREETINGS",
            title = "Hello and I am",
            subtitle = "Master basic introductions and 'to be'",
            progressPercent = if (state.totalXp > 0) 100 else 0,
            onGuidebookClick = {}
        )

        // Bilah Power-Ups (Visual saja untuk saat ini)
        PowerUpsHUDStrip()

        // Kartu Misi Harian
        DailyQuestsCard(
            onSprintClick = { state.firstLessonId?.let(onContinueLearning) }
        )

        // Peta Petualangan Berliku (Serpentine Path)
        SerpentineQuestPath(
            onNodeClick = { node ->
                // Untuk sekarang, klik node manapun akan memulai pelajaran pertama yang tersedia
                state.firstLessonId?.let(onContinueLearning)
            }
        )

        // Tombol Mulai Pelajaran Berikutnya (Sticky di bawah)
        StickyPlayActionLauncher(
            upNextText = "Lesson 1: Hello and I am",
            onStartClick = { state.firstLessonId?.let(onContinueLearning) }
        )

        // Spacer agar konten paling bawah tidak tertutup oleh Bottom Nav Bar melayang
        Spacer(modifier = Modifier.height(100.dp))
    }
}