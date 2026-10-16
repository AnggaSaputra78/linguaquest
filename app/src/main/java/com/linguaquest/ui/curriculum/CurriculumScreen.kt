package com.linguaquest.ui.curriculum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CurriculumScreen(
    viewModel: CurriculumViewModel,
    onOpenLesson: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Kurikulum",
            style = MaterialTheme.typography.headlineSmall
        )

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (!state.isLoading && state.units.isEmpty() && state.errorMessage == null) {
            Text("Kurikulum belum tersedia.")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            state.units.forEach { unitWithLessons ->
                item {
                    Text(
                        text = unitWithLessons.unit.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                items(unitWithLessons.lessons) { lesson ->
                    val progress = state.progressByLesson[lesson.id]
                    val isCompleted = progress?.status == "COMPLETED"

                    Button(
                        onClick = { onOpenLesson(lesson.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = lesson.title,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = if (isCompleted) {
                                    "${lesson.estimatedMinutes} menit • Selesai"
                                } else {
                                    "${lesson.estimatedMinutes} menit"
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}