package test.createx.heartrateapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import test.createx.heartrateapp.presentation.heart_rate.HeartRateScreen
import test.createx.heartrateapp.presentation.heart_rate.HeartRateViewModel
import test.createx.heartrateapp.presentation.onboarding.OnboardingScreen
import test.createx.heartrateapp.presentation.onboarding_data.OnboardingDataScreen
import test.createx.heartrateapp.presentation.onboarding_data.OnboardingDataViewModel
import test.createx.heartrateapp.presentation.paywall.PaywallScreen
import test.createx.heartrateapp.presentation.profile.ProfileScreen
import test.createx.heartrateapp.presentation.report.ReportScreen
import test.createx.heartrateapp.presentation.settings.SettingsScreen
import test.createx.heartrateapp.presentation.statistics.StatisticsScreen
import test.createx.heartrateapp.presentation.workout.WorkoutScreen

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnboardingScreen.route
        ) {
            composable(route = Route.OnboardingScreen.route) {
                OnboardingScreen(navController = navController)
            }
            composable(route = Route.PaywallScreen.route) {
                PaywallScreen(navController = navController)
            }
            composable(route = Route.OnboardingDataScreen.route) {
                val viewModel: OnboardingDataViewModel = hiltViewModel()
                OnboardingDataScreen(viewModel = viewModel)
            }
        }

        navigation(
            route = Route.AppFunctionsNavigation.route,
            startDestination = Route.HeartRateScreen.route
        ) {
            composable(route = Route.HeartRateScreen.route) {
                val viewModel: HeartRateViewModel = hiltViewModel()
                HeartRateScreen(viewModel = viewModel)
            }
            composable(route = Route.ReportScreen.route) {
                ReportScreen()
            }
            composable(route = Route.StatisticsScreen.route) {
                StatisticsScreen()
            }
            composable(route = Route.SettingsScreen.route) {
               SettingsScreen()
            }
            composable(route = Route.ProfileScreen.route) {
                ProfileScreen()
            }
            composable(route = Route.WorkoutScreen.route) {
                WorkoutScreen()
            }
        }
    }
}