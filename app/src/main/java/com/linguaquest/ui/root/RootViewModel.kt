package com.linguaquest.ui.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.linguaquest.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RootUiState(
    val isLoading: Boolean = true,
    val onboardingCompleted: Boolean = false,
    val placementCompleted: Boolean = false,
    val errorMessage: String? = null
)

class RootViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RootUiState())
    val uiState: StateFlow<RootUiState> = _uiState

    init {
        viewModelScope.launch {
            userRepository.observeUserProfile()
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Gagal memuat profil: ${throwable.message}"
                        )
                    }
                }
                .collect { profile ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            onboardingCompleted = profile?.onboardingCompleted ?: false,
                            placementCompleted = profile?.placementCompleted ?: false
                        )
                    }
                }
        }
    }

    companion object {
        fun factory(userRepository: UserRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(RootViewModel::class.java)) {
                        return RootViewModel(userRepository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}