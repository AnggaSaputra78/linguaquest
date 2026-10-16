package com.linguaquest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressScreen() {
    PlaceholderScreen(
        title = "Progres",
        message = "Dashboard progres, statistik keterampilan, dan kompetensi akan dibangun pada Fase 3."
    )
}

@Composable
fun ProfileScreen() {
    PlaceholderScreen(
        title = "Profil",
        message = "Pengaturan profil, target belajar, pengingat, dan preferensi akan dibangun pada fase berikutnya."
    )
}

@Composable
private fun PlaceholderScreen(
    title: String,
    message: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}