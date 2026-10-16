package com.linguaquest.ui.placement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.linguaquest.data.model.ExerciseUi
import com.linguaquest.data.repository.LessonRepository
import com.linguaquest.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlacementUiState(
    val isLoading: Boolean = true,
    val questionIndex: Int = 0,
    val totalQuestions: Int = 0,
    val question: ExerciseUi? = null,
    val selectedOptionId: String? = null,
    val score: Int = 0,
    val isCompleted: Boolean = false,
    val resultLevelId: String? = null,
    val errorMessage: String? = null
)

class PlacementViewModel(
    private val lessonRepository: LessonRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlacementUiState())
    val uiState: StateFlow<PlacementUiState> = _uiState

    private var questions: List<ExerciseUi> = emptyList()

    init {
        loadPlacementQuestions()
    }

    private fun loadPlacementQuestions() {
        viewModelScope.launch {
            try {
                val placementQuestions = lessonRepository.getExercises("placement-l01")

                if (placementQuestions.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isCompleted = true,
                            resultLevelId = "PRE_A1"
                        )
                    }
                    return@launch
                }

                questions = placementQuestions

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        totalQuestions = placementQuestions.size,
                        question = placementQuestions.first()
                    )
                }
            } catch (throwable: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Gagal memuat placement test: ${throwable.message}"
                    )
                }
            }
        }
    }

    fun selectOption(optionId: String) {
        if (_uiState.value.isCompleted) return

        _uiState.update {
            it.copy(selectedOptionId = optionId)
        }
    }

    fun next() {
        val state = _uiState.value
        val currentQuestion = state.question ?: return

        if (state.selectedOptionId == null) return

        val isCorrect = currentQuestion.options
            .find { it.id == state.selectedOptionId }
            ?.isCorrect == true

        val newScore = if (isCorrect) state.score + 1 else state.score
        val nextIndex = state.questionIndex + 1

        if (nextIndex >= questions.size) {
            val percent = if (questions.isNotEmpty()) {
                newScore * 100 / questions.size
            } else {
                0
            }

            val levelId = if (percent >= 60) {
                "A1"
            } else {
                "PRE_A1"
            }

            _uiState.update {
                it.copy(
                    score = newScore,
                    isCompleted = true,
                    resultLevelId = levelId
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    score = newScore,
                    questionIndex = nextIndex,
                    question = questions[nextIndex],
                    selectedOptionId = null
                )
            }
        }
    }

    fun startLearning() {
        val levelId = _uiState.value.resultLevelId ?: "PRE_A1"

        viewModelScope.launch {
            userRepository.completePlacement(levelId)
        }
    }

    companion object {
        fun factory(
            lessonRepository: LessonRepository,
            userRepository: UserRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(PlacementViewModel::class.java)) {
                        return PlacementViewModel(lessonRepository, userRepository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}