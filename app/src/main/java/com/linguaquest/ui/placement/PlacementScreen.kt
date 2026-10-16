package com.linguaquest.ui.placement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlacementScreen(
    viewModel: PlacementViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Placement Test",
            style = MaterialTheme.typography.headlineSmall
        )

        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage ?: "Terjadi kesalahan.",
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.isCompleted -> {
                PlacementCompletedContent(
                    score = state.score,
                    total = state.totalQuestions,
                    resultLevelId = state.resultLevelId,
                    onStartLearning = viewModel::startLearning
                )
            }

            state.question != null -> {
                val question = state.question!!

                Text(
                    text = "Pertanyaan ${state.questionIndex + 1} dari ${state.totalQuestions}",
                    style = MaterialTheme.typography.bodySmall
                )

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = question.instruction,
                            style = MaterialTheme.typography.titleSmall
                        )

                        Text(
                            text = question.prompt,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            question.options.forEach { option ->
                                val selected = state.selectedOptionId == option.id

                                if (selected) {
                                    Button(
                                        onClick = { viewModel.selectOption(option.id) },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(option.text)
                                    }
                                } else {
                                    OutlinedButton(
                                        onClick = { viewModel.selectOption(option.id) },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(option.text)
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = viewModel::next,
                            enabled = state.selectedOptionId != null,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                if (state.questionIndex + 1 >= state.totalQuestions) {
                                    "Selesai"
                                } else {
                                    "Lanjut"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlacementCompletedContent(
    score: Int,
    total: Int,
    resultLevelId: String?,
    onStartLearning: () -> Unit
) {
    val levelLabel = when (resultLevelId) {
        "A1" -> "A1 - Beginner"
        else -> "Pre-A1 - Fondasi"
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Placement test selesai",
                style = MaterialTheme.typography.titleMedium
            )

            Text("Skor kamu: $score dari $total")
            Text("Level awal yang direkomendasikan: $levelLabel")
            Text("Level ini digunakan untuk menyesuaikan rekomendasi belajar awal.")

            Button(
                onClick = onStartLearning,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mulai Belajar")
            }
        }
    }
}