package com.linguaquest.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.linguaquest.di.AppContainer
import com.linguaquest.ui.components.FloatingBottomNavBar
import com.linguaquest.ui.components.LinguaColors
import com.linguaquest.ui.curriculum.CurriculumScreen
import com.linguaquest.ui.curriculum.CurriculumViewModel
import com.linguaquest.ui.home.HomeViewModel
import com.linguaquest.ui.lesson.LessonScreen
import com.linguaquest.ui.lesson.LessonViewModel
import com.linguaquest.ui.navigation.TopLevelDestination
import com.linguaquest.ui.practice.PracticeScreen
import com.linguaquest.ui.review.ReviewScreen
import com.linguaquest.ui.review.ReviewViewModel
import com.linguaquest.ui.screens.HomeScreen
import com.linguaquest.ui.screens.ProfileScreen
import com.linguaquest.ui.screens.ProgressScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    container: AppContainer
) {
    val navController = rememberNavController()

    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.factory(
            container.contentRepository,
            container.progressRepository,
            container.userRepository,
            container.streakRepository
        )
    )

    val curriculumViewModel: CurriculumViewModel = viewModel(
        factory = CurriculumViewModel.factory(container.curriculumRepository)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedTab = when (currentRoute) {
        TopLevelDestination.HOME.route -> "learn"
        TopLevelDestination.CURRICULUM.route -> "quests"
        TopLevelDestination.PROGRESS.route -> "ranks"
        TopLevelDestination.PROFILE.route -> "profile"
        else -> "learn"
    }

    Scaffold(
        containerColor = LinguaColors.Surface,
        bottomBar = {
            FloatingBottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    val route = when (tab) {
                        "learn" -> TopLevelDestination.HOME.route
                        "quests" -> TopLevelDestination.CURRICULUM.route
                        "ranks" -> TopLevelDestination.PROGRESS.route
                        "profile" -> TopLevelDestination.PROFILE.route
                        else -> TopLevelDestination.HOME.route
                    }
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onQuickReviewClick = {
                    navController.navigate("review")
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TopLevelDestination.HOME.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TopLevelDestination.HOME.route) {
                HomeScreen(
                    homeViewModel = homeViewModel,
                    onContinueLearning = { lessonId ->
                        navController.navigate("lesson/$lessonId")
                    }
                )
            }

            composable(TopLevelDestination.CURRICULUM.route) {
                CurriculumScreen(
                    viewModel = curriculumViewModel,
                    onOpenLesson = { lessonId ->
                        navController.navigate("lesson/$lessonId")
                    }
                )
            }

            composable(TopLevelDestination.PRACTICE.route) {
                PracticeScreen(
                    viewModel = curriculumViewModel,
                    onOpenLesson = { lessonId ->
                        navController.navigate("lesson/$lessonId")
                    }
                )
            }

            composable(TopLevelDestination.PROGRESS.route) {
                ProgressScreen()
            }

            composable(TopLevelDestination.PROFILE.route) {
                ProfileScreen()
            }

            composable("lesson/{lessonId}") { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString("lessonId").orEmpty()

                val lessonViewModel: LessonViewModel = viewModel(
                    key = lessonId,
                    factory = LessonViewModel.factory(
                        container.lessonRepository,
                        lessonId
                    )
                )

                LessonScreen(
                    viewModel = lessonViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable("review") {
                val reviewViewModel: ReviewViewModel = viewModel(
                    factory = ReviewViewModel.factory(
                        container.spacedRepetitionRepository,
                        container.streakRepository
                    )
                )
                ReviewScreen(
                    viewModel = reviewViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}