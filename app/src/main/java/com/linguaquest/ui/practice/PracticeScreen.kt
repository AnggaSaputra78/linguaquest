package com.linguaquest.ui.practice

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
import com.linguaquest.ui.curriculum.CurriculumViewModel

@Composable
fun PracticeScreen(
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
            text = "Latihan Review",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Pilih pelajaran untuk mengulang materi.",
            style = MaterialTheme.typography.bodyLarge
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
            Text("Belum ada pelajaran untuk direview.")
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
                                    "${lesson.estimatedMinutes} menit • Sudah selesai"
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