package com.linguaquest.ui.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.linguaquest.data.model.ExerciseType
import com.linguaquest.data.model.ExerciseUi
import com.linguaquest.data.model.MatchingOptionUi
import com.linguaquest.data.model.MatchingPairUi
import com.linguaquest.data.model.WordChip
import com.linguaquest.data.repository.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

data class LessonUiState(
    val isLoading: Boolean = true,
    val lessonTitle: String = "",
    val exerciseIndex: Int = 0,
    val totalExercises: Int = 0,
    val exercise: ExerciseUi? = null,
    val selectedOptionId: String? = null,
    val textAnswer: String = "",
    val availableWords: List<WordChip> = emptyList(),
    val selectedWords: List<WordChip> = emptyList(),
    val matchingPairs: List<MatchingPairUi> = emptyList(),
    val matchingRightOptions: List<MatchingOptionUi> = emptyList(),
    val selectedMatchingLeftId: String? = null,
    val matchedAnswers: Map<String, String> = emptyMap(),
    val isSubmitted: Boolean = false,
    val isCorrect: Boolean = false,
    val feedbackTitle: String = "",
    val feedbackMessage: String = "",
    val correctAnswers: Int = 0,
    val isCompleted: Boolean = false,
    val errorMessage: String? = null
)

class LessonViewModel(
    private val repository: LessonRepository,
    private val lessonId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(LessonUiState())
    val uiState: StateFlow<LessonUiState> = _uiState

    private var exerciseList: List<ExerciseUi> = emptyList()

    init {
        loadLesson()
    }

    private fun loadLesson() {
        viewModelScope.launch {
            try {
                val lesson = repository.getLesson(lessonId)

                if (lesson == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Pelajaran tidak ditemukan."
                        )
                    }
                    return@launch
                }

                val exercises = repository.getExercises(lessonId)
                exerciseList = exercises

                if (exercises.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            lessonTitle = lesson.title,
                            errorMessage = "Belum ada latihan pada pelajaran ini."
                        )
                    }
                    return@launch
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        lessonTitle = lesson.title,
                        totalExercises = exercises.size
                    )
                }

                loadExercise(0)
            } catch (throwable: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Gagal memuat pelajaran: ${throwable.message}"
                    )
                }
            }
        }
    }

    private fun loadExercise(index: Int) {
        val exercise = exerciseList.getOrNull(index) ?: return

        val availableWords = if (exercise.type == ExerciseType.SENTENCE_BUILD) {
            buildWordChips(exercise)
        } else {
            emptyList()
        }

        val matchingPairs = if (exercise.type == ExerciseType.MATCHING) {
            buildMatchingPairs(exercise)
        } else {
            emptyList()
        }

        val matchingRightOptions = matchingPairs
            .map { pair ->
                MatchingOptionUi(
                    id = "${pair.id}-right",
                    text = pair.right
                )
            }
            .shuffled(Random(exercise.id.hashCode()))

        _uiState.update {
            it.copy(
                exerciseIndex = index,
                exercise = exercise,
                selectedOptionId = null,
                textAnswer = "",
                availableWords = availableWords,
                selectedWords = emptyList(),
                matchingPairs = matchingPairs,
                matchingRightOptions = matchingRightOptions,
                selectedMatchingLeftId = null,
                matchedAnswers = emptyMap(),
                isSubmitted = false,
                isCorrect = false,
                feedbackTitle = "",
                feedbackMessage = ""
            )
        }
    }

    private fun buildWordChips(exercise: ExerciseUi): List<WordChip> {
        val words = exercise.answer
            .trim()
            .split(Regex("\\s+"))
            .filter { it.isNotEmpty() }

        val random = Random(exercise.id.hashCode())

        return words
            .withIndex()
            .map { indexedWord ->
                WordChip(
                    id = "${exercise.id}-word-${indexedWord.index}",
                    text = indexedWord.value
                )
            }
            .shuffled(random)
    }

    private fun buildMatchingPairs(exercise: ExerciseUi): List<MatchingPairUi> {
        return exercise.options.mapNotNull { option ->
            val parts = option.text.split("|")

            if (parts.size >= 2) {
                MatchingPairUi(
                    id = option.id,
                    left = parts[0].trim(),
                    right = parts[1].trim()
                )
            } else {
                null
            }
        }
    }

    fun selectOption(optionId: String) {
        if (_uiState.value.isSubmitted) return

        _uiState.update {
            it.copy(selectedOptionId = optionId)
        }
    }

    fun onTextChange(text: String) {
        if (_uiState.value.isSubmitted) return

        _uiState.update {
            it.copy(textAnswer = text)
        }
    }

    fun clickAvailableWord(word: WordChip) {
        if (_uiState.value.isSubmitted) return

        _uiState.update { state ->
            state.copy(
                availableWords = state.availableWords.filter { it.id != word.id },
                selectedWords = state.selectedWords + word
            )
        }
    }

    fun clickSelectedWord(word: WordChip) {
        if (_uiState.value.isSubmitted) return

        _uiState.update { state ->
            state.copy(
                selectedWords = state.selectedWords.filter { it.id != word.id },
                availableWords = state.availableWords + word
            )
        }
    }

    fun selectMatchingLeft(pairId: String) {
        if (_uiState.value.isSubmitted) return

        _uiState.update { state ->
            state.copy(
                selectedMatchingLeftId = if (state.selectedMatchingLeftId == pairId) {
                    null
                } else {
                    pairId
                }
            )
        }
    }

    fun selectMatchingRight(optionId: String) {
        val state = _uiState.value

        if (state.isSubmitted) return

        val leftId = state.selectedMatchingLeftId ?: return

        val updatedAnswers = state.matchedAnswers.toMutableMap()

        val existingLeft = updatedAnswers.entries
            .firstOrNull { it.value == optionId }
            ?.key

        if (existingLeft != null) {
            updatedAnswers.remove(existingLeft)
        }

        updatedAnswers[leftId] = optionId

        _uiState.update {
            it.copy(
                matchedAnswers = updatedAnswers,
                selectedMatchingLeftId = null
            )
        }
    }

    fun unmatchMatchingLeft(pairId: String) {
        if (_uiState.value.isSubmitted) return

        _uiState.update { state ->
            val updatedAnswers = state.matchedAnswers.toMutableMap()
            updatedAnswers.remove(pairId)

            state.copy(
                matchedAnswers = updatedAnswers,
                selectedMatchingLeftId = pairId
            )
        }
    }

    fun submitAnswer() {
        val state = _uiState.value
        val exercise = state.exercise ?: return

        if (state.isSubmitted) return

        val evaluation = evaluateAnswer(exercise, state)
        val isCorrect = evaluation.first
        val answerText = evaluation.second

        val correctAnswers = if (isCorrect) {
            state.correctAnswers + 1
        } else {
            state.correctAnswers
        }

        _uiState.update {
            it.copy(
                isSubmitted = true,
                isCorrect = isCorrect,
                correctAnswers = correctAnswers,
                feedbackTitle = if (isCorrect) "Benar" else "Belum tepat",
                feedbackMessage = exercise.explanation
            )
        }

        viewModelScope.launch {
            repository.saveAttempt(
                exerciseId = exercise.id,
                lessonId = lessonId,
                isCorrect = isCorrect,
                answerText = answerText
            )
        }
    }

    private fun evaluateAnswer(
        exercise: ExerciseUi,
        state: LessonUiState
    ): Pair<Boolean, String> {
        return when (exercise.type) {
            ExerciseType.MULTIPLE_CHOICE -> {
                val option = exercise.options.find { it.id == state.selectedOptionId }
                Pair(option?.isCorrect == true, option?.text.orEmpty())
            }

            ExerciseType.FILL_BLANK -> {
                val userAnswer = state.textAnswer
                val isCorrect = normalizeText(userAnswer) == normalizeText(exercise.answer)
                Pair(isCorrect, userAnswer.trim())
            }

            ExerciseType.SENTENCE_BUILD -> {
                val userAnswer = state.selectedWords.joinToString(" ") { it.text }
                val isCorrect = normalizeText(userAnswer) == normalizeText(exercise.answer)
                Pair(isCorrect, userAnswer.trim())
            }

            ExerciseType.MATCHING -> {
                val isCorrect = state.matchingPairs.isNotEmpty() &&
                    state.matchingPairs.all { pair ->
                        state.matchedAnswers[pair.id] == "${pair.id}-right"
                    }

                val answerText = state.matchingPairs.joinToString("; ") { pair ->
                    val rightOptionId = state.matchedAnswers[pair.id]
                    val rightText = state.matchingRightOptions
                        .find { it.id == rightOptionId }
                        ?.text
                        .orEmpty()

                    "${pair.left}=$rightText"
                }

                Pair(isCorrect, answerText)
            }

            else -> Pair(false, "")
        }
    }

    private fun normalizeText(text: String): String {
        return text
            .trim()
            .replace(Regex("\\s+"), " ")
            .lowercase()
    }

    fun next() {
        val state = _uiState.value

        if (!state.isSubmitted) return

        if (state.exerciseIndex + 1 >= state.totalExercises) {
            completeLesson()
        } else {
            loadExercise(state.exerciseIndex + 1)
        }
    }

    private fun completeLesson() {
        val state = _uiState.value
        val masteryScore = if (state.totalExercises > 0) {
            state.correctAnswers * 100 / state.totalExercises
        } else {
            0
        }

        _uiState.update {
            it.copy(
                isCompleted = true,
                exercise = null
            )
        }

        viewModelScope.launch {
            repository.completeLesson(
                lessonId = lessonId,
                masteryScore = masteryScore
            )
        }
    }

    fun retry() {
        _uiState.update {
            it.copy(
                isCompleted = false,
                correctAnswers = 0
            )
        }

        if (exerciseList.isNotEmpty()) {
            loadExercise(0)
        }
    }

    companion object {
        fun factory(
            repository: LessonRepository,
            lessonId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
                    return LessonViewModel(repository, lessonId) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}