package com.linguaquest.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.linguaquest.data.local.entity.VocabularyEntity
import com.linguaquest.data.repository.SpacedRepetitionRepository
import com.linguaquest.data.repository.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ReviewUiState(
    val isLoading: Boolean = true,
    val vocabList: List<VocabularyEntity> = emptyList(),
    val currentIndex: Int = 0,
    val isFlipped: Boolean = false,
    val isCompleted: Boolean = false,
    val errorMessage: String? = null
)

class ReviewViewModel(
    private val srsRepository: SpacedRepetitionRepository,
    private val streakRepository: StreakRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState

    init {
        loadDueVocabulary()
    }

    private fun loadDueVocabulary() {
        viewModelScope.launch {
            val dueVocab = srsRepository.getDueVocabulary(limit = 20)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    vocabList = dueVocab,
                    isCompleted = dueVocab.isEmpty()
                )
            }
        }
    }

    fun flipCard() {
        _uiState.update { it.copy(isFlipped = !it.isFlipped) }
    }

    fun onRemember() {
        processAnswer(isCorrect = true)
    }

    fun onForget() {
        processAnswer(isCorrect = false)
    }

    private fun processAnswer(isCorrect: Boolean) {
        val state = _uiState.value
        val currentVocab = state.vocabList.getOrNull(state.currentIndex) ?: return

        viewModelScope.launch {
            srsRepository.recordReview(currentVocab.id, isCorrect)
            streakRepository.recordActivity(xpEarned = if (isCorrect) 5 else 1, minutesStudied = 0)

            val nextIndex = state.currentIndex + 1
            if (nextIndex >= state.vocabList.size) {
                _uiState.update { it.copy(isCompleted = true) }
            } else {
                _uiState.update {
                    it.copy(
                        currentIndex = nextIndex,
                        isFlipped = false
                    )
                }
            }
        }
    }

    companion object {
        fun factory(
            srsRepository: SpacedRepetitionRepository,
            streakRepository: StreakRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
                        return ReviewViewModel(srsRepository, streakRepository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}