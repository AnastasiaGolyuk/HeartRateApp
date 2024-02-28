package test.createx.heartrateapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import test.createx.heartrateapp.presentation.heart_rate.HeartRateScreen
import test.createx.heartrateapp.presentation.heart_rate.HeartRateViewModel
import test.createx.heartrateapp.presentation.profile.ProfileScreen
import test.createx.heartrateapp.presentation.profile.ProfileViewModel
import test.createx.heartrateapp.presentation.report.ReportScreen
import test.createx.heartrateapp.presentation.settings.SettingsScreen
import test.createx.heartrateapp.presentation.statistics.StatisticsScreen
import test.createx.heartrateapp.presentation.topAppBar.TopAppBarNavigationState
import test.createx.heartrateapp.presentation.workout.WorkoutScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    onComposing: (TopAppBarNavigationState) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Route.HeartRateScreen.route
    ) {

            composable(route = Route.HeartRateScreen.route) {
                val viewModel: HeartRateViewModel = hiltViewModel()
                HeartRateScreen(viewModel = viewModel, onComposing = onComposing)
            }
            composable(route = Route.ReportScreen.route) {
                ReportScreen()
            }
            composable(route = Route.StatisticsScreen.route) {
                StatisticsScreen()
            }
            composable(route = Route.SettingsScreen.route) {
                SettingsScreen(navController = navController)
            }
            composable(route = Route.ProfileScreen.route) {
                val viewModel: ProfileViewModel = hiltViewModel()
                ProfileScreen(viewModel = viewModel, onComposing = onComposing, navController = navController)
            }
            composable(route = Route.WorkoutScreen.route) {
                WorkoutScreen()
            }
    }
}