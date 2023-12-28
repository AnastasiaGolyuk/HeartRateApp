package test.createx.heartrateapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import test.createx.heartrateapp.presentation.heart_rate.HeartRateScreen
import test.createx.heartrateapp.presentation.onboarding.OnboardingScreen
import test.createx.heartrateapp.presentation.onboarding.OnboardingViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnboardingScreen.route
        ) {
            composable(route = Route.OnboardingScreen.route) {
                val viewModel: OnboardingViewModel = hiltViewModel()
                OnboardingScreen(onEvent = viewModel::onEvent)
            }
        }

        navigation(
            route = Route.AppFunctionsNavigation.route,
            startDestination = Route.HeartRateScreen.route
        ) {
            composable(route = Route.HeartRateScreen.route) {
                HeartRateScreen()
            }
        }
    }
}