package com.linguaquest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.linguaquest.data.repository.ContentRepository
import com.linguaquest.data.repository.ProgressRepository
import com.linguaquest.data.repository.StreakRepository
import com.linguaquest.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = true,
    val courseCount: Int = 0,
    val totalXp: Int = 0,
    val streakDays: Int = 0,
    val firstLessonId: String? = null,
    val displayName: String = "Pembelajar",
    val currentLevelId: String? = null,
    val targetMinutesPerDay: Int = 30,
    val errorMessage: String? = null
)

class HomeViewModel(
    private val contentRepository: ContentRepository,
    private val progressRepository: ProgressRepository,
    private val userRepository: UserRepository,
    private val streakRepository: StreakRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            contentRepository.observeCourseCount()
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Gagal memuat data: ${throwable.message}"
                        )
                    }
                }
                .collect { count ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            courseCount = count
                        )
                    }
                }
        }

        viewModelScope.launch {
            progressRepository.observeTotalXp()
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            errorMessage = "Gagal memuat XP: ${throwable.message}"
                        )
                    }
                }
                .collect { xp ->
                    _uiState.update {
                        it.copy(totalXp = xp)
                    }
                }
        }

        viewModelScope.launch {
            userRepository.observeUserProfile()
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            errorMessage = "Gagal memuat profil: ${throwable.message}"
                        )
                    }
                }
                .collect { profile ->
                    _uiState.update {
                        it.copy(
                            displayName = profile?.displayName ?: "Pembelajar",
                            currentLevelId = profile?.currentLevelId,
                            targetMinutesPerDay = profile?.targetMinutesPerDay ?: 30
                        )
                    }
                }
        }

        viewModelScope.launch {
            try {
                val streak = streakRepository.getCurrentStreak()
                _uiState.update { it.copy(streakDays = streak) }
            } catch (e: Exception) {
                // Ignore streak calculation errors silently
            }
        }

        viewModelScope.launch {
            try {
                val firstLessonId = contentRepository.getFirstLessonRoute()
                _uiState.update {
                    it.copy(firstLessonId = firstLessonId)
                }
            } catch (throwable: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Gagal menyiapkan pelajaran pertama: ${throwable.message}"
                    )
                }
            }
        }
    }

    companion object {
        fun factory(
            contentRepository: ContentRepository,
            progressRepository: ProgressRepository,
            userRepository: UserRepository,
            streakRepository: StreakRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                        return HomeViewModel(
                            contentRepository,
                            progressRepository,
                            userRepository,
                            streakRepository
                        ) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}