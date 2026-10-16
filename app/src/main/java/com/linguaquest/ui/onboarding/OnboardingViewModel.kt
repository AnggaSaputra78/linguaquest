package com.linguaquest.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.linguaquest.data.repository.UserRepository
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun completeOnboarding(
        displayName: String,
        targetMinutesPerDay: Int
    ) {
        viewModelScope.launch {
            userRepository.completeOnboarding(
                displayName = displayName,
                targetMinutesPerDay = targetMinutesPerDay
            )
        }
    }

    companion object {
        fun factory(userRepository: UserRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
                        return OnboardingViewModel(userRepository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}