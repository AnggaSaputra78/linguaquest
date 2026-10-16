package com.linguaquest.ui.lesson

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.linguaquest.data.model.ExerciseType
import com.linguaquest.data.model.ExerciseUi

@Composable
fun LessonScreen(
    viewModel: LessonViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = onBack) {
                Text("Kembali")
            }

            Text(
                text = state.lessonTitle,
                style = MaterialTheme.typography.titleMedium
            )
        }

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
                CompletionContent(
                    correctAnswers = state.correctAnswers,
                    totalExercises = state.totalExercises,
                    onBack = onBack,
                    onRetry = viewModel::retry
                )
            }

            state.exercise != null -> {
                val progress = if (state.totalExercises > 0) {
                    ((state.exerciseIndex + 1).toFloat() / state.totalExercises.toFloat())
                        .coerceIn(0f, 1f)
                } else {
                    0f
                }

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Soal ${state.exerciseIndex + 1} dari ${state.totalExercises}",
                    style = MaterialTheme.typography.bodySmall
                )

                ExerciseContent(
                    state = state,
                    exercise = state.exercise!!,
                    onSelectOption = viewModel::selectOption,
                    onTextChange = viewModel::onTextChange,
                    onClickAvailableWord = viewModel::clickAvailableWord,
                    onClickSelectedWord = viewModel::clickSelectedWord,
                    onClickMatchingLeft = viewModel::selectMatchingLeft,
                    onClickMatchingRight = viewModel::selectMatchingRight,
                    onUnmatchMatchingLeft = viewModel::unmatchMatchingLeft,
                    onSubmit = viewModel::submitAnswer,
                    onNext = viewModel::next
                )
            }
        }
    }
}

@Composable
private fun ExerciseContent(
    state: LessonUiState,
    exercise: ExerciseUi,
    onSelectOption: (String) -> Unit,
    onTextChange: (String) -> Unit,
    onClickAvailableWord: (com.linguaquest.data.model.WordChip) -> Unit,
    onClickSelectedWord: (com.linguaquest.data.model.WordChip) -> Unit,
    onClickMatchingLeft: (String) -> Unit,
    onClickMatchingRight: (String) -> Unit,
    onUnmatchMatchingLeft: (String) -> Unit,
    onSubmit: () -> Unit,
    onNext: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = exercise.instruction,
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = exercise.prompt,
                style = MaterialTheme.typography.bodyLarge
            )

            when (exercise.type) {
                ExerciseType.MULTIPLE_CHOICE -> {
                    MultipleChoiceContent(
                        state = state,
                        exercise = exercise,
                        onSelectOption = onSelectOption
                    )
                }

                ExerciseType.FILL_BLANK -> {
                    OutlinedTextField(
                        value = state.textAnswer,
                        onValueChange = onTextChange,
                        enabled = !state.isSubmitted,
                        label = { Text("Jawabanmu") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                ExerciseType.SENTENCE_BUILD -> {
                    SentenceBuildContent(
                        state = state,
                        onClickAvailableWord = onClickAvailableWord,
                        onClickSelectedWord = onClickSelectedWord
                    )
                }

                ExerciseType.MATCHING -> {
                    MatchingContent(
                        state = state,
                        onClickMatchingLeft = onClickMatchingLeft,
                        onClickMatchingRight = onClickMatchingRight,
                        onUnmatchMatchingLeft = onUnmatchMatchingLeft
                    )
                }
            }

            if (!state.isSubmitted) {
                Button(
                    onClick = onSubmit,
                    enabled = isSubmitEnabled(state),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Periksa Jawaban")
                }
            } else {
                FeedbackContent(state = state, exercise = exercise)

                Button(
                    onClick = onNext,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (state.exerciseIndex + 1 >= state.totalExercises) {
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

private fun isSubmitEnabled(state: LessonUiState): Boolean {
    val exercise = state.exercise ?: return false

    return when (exercise.type) {
        ExerciseType.MULTIPLE_CHOICE -> state.selectedOptionId != null
        ExerciseType.FILL_BLANK -> state.textAnswer.isNotBlank()
        ExerciseType.SENTENCE_BUILD -> state.selectedWords.isNotEmpty()
        ExerciseType.MATCHING -> state.matchedAnswers.size == state.matchingPairs.size
        else -> false
    }
}

@Composable
private fun MultipleChoiceContent(
    state: LessonUiState,
    exercise: ExerciseUi,
    onSelectOption: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        exercise.options.forEach { option ->
            val selected = state.selectedOptionId == option.id

            if (selected) {
                Button(
                    onClick = { onSelectOption(option.id) },
                    enabled = !state.isSubmitted,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(option.text)
                }
            } else {
                OutlinedButton(
                    onClick = { onSelectOption(option.id) },
                    enabled = !state.isSubmitted,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(option.text)
                }
            }
        }
    }
}

@Composable
private fun SentenceBuildContent(
    state: LessonUiState,
    onClickAvailableWord: (com.linguaquest.data.model.WordChip) -> Unit,
    onClickSelectedWord: (com.linguaquest.data.model.WordChip) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Kalimatmu:",
            style = MaterialTheme.typography.bodySmall
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.selectedWords.isEmpty()) {
                Text(
                    text = "Ketuk kata di bawah untuk menyusun kalimat.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            state.selectedWords.forEach { word ->
                OutlinedButton(
                    onClick = { onClickSelectedWord(word) },
                    enabled = !state.isSubmitted
                ) {
                    Text(word.text)
                }
            }
        }

        Text(
            text = "Pilihan kata:",
            style = MaterialTheme.typography.bodySmall
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.availableWords.forEach { word ->
                Button(
                    onClick = { onClickAvailableWord(word) },
                    enabled = !state.isSubmitted
                ) {
                    Text(word.text)
                }
            }
        }
    }
}

@Composable
private fun MatchingContent(
    state: LessonUiState,
    onClickMatchingLeft: (String) -> Unit,
    onClickMatchingRight: (String) -> Unit,
    onUnmatchMatchingLeft: (String) -> Unit
) {
    val usedRightOptionIds = state.matchedAnswers.values.toSet()
    val availableRightOptions = state.matchingRightOptions.filterNot {
        usedRightOptionIds.contains(it.id)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Pilih kata di kiri, lalu pilih pasangan di kanan.",
            style = MaterialTheme.typography.bodySmall
        )

        state.matchingPairs.forEach { pair ->
            val matchedOptionId = state.matchedAnswers[pair.id]
            val matchedText = state.matchingRightOptions
                .find { it.id == matchedOptionId }
                ?.text

            val isSelected = state.selectedMatchingLeftId == pair.id

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (isSelected) {
                    Button(
                        onClick = { onClickMatchingLeft(pair.id) },
                        enabled = !state.isSubmitted,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(pair.left)
                    }
                } else {
                    OutlinedButton(
                        onClick = { onClickMatchingLeft(pair.id) },
                        enabled = !state.isSubmitted,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(pair.left)
                    }
                }

                if (matchedText != null) {
                    OutlinedButton(
                        onClick = { onUnmatchMatchingLeft(pair.id) },
                        enabled = !state.isSubmitted,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(matchedText)
                    }
                } else {
                    Text(
                        text = "—",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Text(
            text = "Pilihan jawaban:",
            style = MaterialTheme.typography.bodySmall
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            availableRightOptions.forEach { option ->
                OutlinedButton(
                    onClick = { onClickMatchingRight(option.id) },
                    enabled = !state.isSubmitted && state.selectedMatchingLeftId != null,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(option.text)
                }
            }
        }
    }
}

@Composable
private fun FeedbackContent(
    state: LessonUiState,
    exercise: ExerciseUi
) {
    val containerColor = if (state.isCorrect) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.errorContainer
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = state.feedbackTitle,
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = state.feedbackMessage,
                style = MaterialTheme.typography.bodyMedium
            )

            if (!state.isCorrect) {
                Text(
                    text = "Jawaban benar: ${exercise.answer}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun CompletionContent(
    correctAnswers: Int,
    totalExercises: Int,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    val masteryScore = if (totalExercises > 0) {
        correctAnswers * 100 / totalExercises
    } else {
        0
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Pelajaran selesai",
                style = MaterialTheme.typography.titleMedium
            )

            Text("Jawaban benar: $correctAnswers dari $totalExercises")
            Text("Skor penguasaan: $masteryScore%")
            Text("Progres dan XP disimpan.")

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kembali ke Beranda")
            }

            OutlinedButton(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ulangi Pelajaran")
            }
        }
    }
}