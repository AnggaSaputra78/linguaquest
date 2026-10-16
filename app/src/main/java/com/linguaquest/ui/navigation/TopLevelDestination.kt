package com.linguaquest.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    HOME(
        route = "home",
        label = "Beranda",
        icon = Icons.Filled.Home
    ),
    CURRICULUM(
        route = "curriculum",
        label = "Kurikulum",
        icon = Icons.Filled.List
    ),
    PRACTICE(
        route = "practice",
        label = "Latihan",
        icon = Icons.Filled.PlayArrow
    ),
    PROGRESS(
        route = "progress",
        label = "Progres",
        icon = Icons.Filled.Star
    ),
    PROFILE(
        route = "profile",
        label = "Profil",
        icon = Icons.Filled.Person
    )
}