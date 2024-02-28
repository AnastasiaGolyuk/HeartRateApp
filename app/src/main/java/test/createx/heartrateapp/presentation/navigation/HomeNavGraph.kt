package test.createx.heartrateapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import test.createx.heartrateapp.presentation.heart_rate.HeartRateScreen
import test.createx.heartrateapp.presentation.heart_rate_measurement.HeartRateMeasurementScreen
import test.createx.heartrateapp.presentation.heart_rate_measurement.HeartRateMeasurementViewModel
import test.createx.heartrateapp.presentation.heart_rate_report.HeartRateReportScreen
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
        route = Graph.HomeGraph.route,
        startDestination = Route.HeartRateScreen.route
    ) {
        heartRateNavGraph(navController = navController, onComposing = onComposing)
        composable(route = Route.HeartRateScreen.route) {
            HeartRateScreen(navController = navController)
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
            ProfileScreen(
                viewModel = viewModel,
                onComposing = onComposing,
                navController = navController
            )
        }
        composable(route = Route.WorkoutScreen.route) {
            WorkoutScreen()
        }
    }
}

fun NavGraphBuilder.heartRateNavGraph(
    navController: NavHostController,
    onComposing: (TopAppBarNavigationState) -> Unit
) {
    navigation(
        startDestination = Route.HeartRateMeasurementScreen.route,
        route= Graph.HeartMeasurementGraph.route,
    )
    {
        composable(route = Route.HeartRateMeasurementScreen.route) {
            val viewModel: HeartRateMeasurementViewModel = hiltViewModel()
            HeartRateMeasurementScreen(
                viewModel = viewModel,
                onComposing = onComposing,
                navController = navController
            )
        }
        composable(route = Route.HeartRateReportScreen.route) {
            HeartRateReportScreen()
        }
    }
}