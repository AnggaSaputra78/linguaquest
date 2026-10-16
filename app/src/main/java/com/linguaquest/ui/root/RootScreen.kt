package com.linguaquest.ui.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.linguaquest.di.AppContainer
import com.linguaquest.ui.MainScreen
import com.linguaquest.ui.onboarding.OnboardingScreen
import com.linguaquest.ui.onboarding.OnboardingViewModel
import com.linguaquest.ui.placement.PlacementScreen
import com.linguaquest.ui.placement.PlacementViewModel

@Composable
fun RootScreen(
    container: AppContainer
) {
    val rootViewModel: RootViewModel = viewModel(
        factory = RootViewModel.factory(container.userRepository)
    )

    val state by rootViewModel.uiState.collectAsState()

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        !state.onboardingCompleted -> {
            val onboardingViewModel: OnboardingViewModel = viewModel(
                factory = OnboardingViewModel.factory(container.userRepository)
            )

            OnboardingScreen(viewModel = onboardingViewModel)
        }

        !state.placementCompleted -> {
            val placementViewModel: PlacementViewModel = viewModel(
                factory = PlacementViewModel.factory(
                    container.lessonRepository,
                    container.userRepository
                )
            )

            PlacementScreen(viewModel = placementViewModel)
        }

        else -> {
            MainScreen(container = container)
        }
    }
}