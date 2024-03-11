package test.createx.heartrateapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import test.createx.heartrateapp.presentation.heart_rate.HeartRateScreen
import test.createx.heartrateapp.presentation.heart_rate_measurement.HeartRateMeasurementScreen
import test.createx.heartrateapp.presentation.heart_rate_measurement.HeartRateMeasurementViewModel
import test.createx.heartrateapp.presentation.heart_rate_report.HeartRateReportScreen
import test.createx.heartrateapp.presentation.heart_rate_report.HeartRateReportViewModel
import test.createx.heartrateapp.presentation.profile.ProfileScreen
import test.createx.heartrateapp.presentation.profile.ProfileViewModel
import test.createx.heartrateapp.presentation.report.ReportScreen
import test.createx.heartrateapp.presentation.report.ReportViewModel
import test.createx.heartrateapp.presentation.settings.SettingsScreen
import test.createx.heartrateapp.presentation.statistics.StatisticsScreen
import test.createx.heartrateapp.presentation.statistics.StatisticsViewModel
import test.createx.heartrateapp.presentation.topAppBar.TopAppBarNavigationState
import test.createx.heartrateapp.presentation.workout.WorkoutScreen
import test.createx.heartrateapp.presentation.workout_exercises.WorkoutExerciseScreen
import test.createx.heartrateapp.presentation.workout_exercises.WorkoutExerciseViewModel

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
        workoutNavGraph(navController = navController, onComposing = onComposing)
        composable(route = Route.HeartRateScreen.route) {
            HeartRateScreen(navController = navController)
        }
        composable(route = Route.ReportScreen.route) {
            val viewModel: ReportViewModel = hiltViewModel()
            ReportScreen(
                navController = navController, viewModel = viewModel
            )
        }
        composable(route = Route.StatisticsScreen.route) {
            val viewModel: StatisticsViewModel = hiltViewModel()
            StatisticsScreen(navController = navController, viewModel = viewModel)
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
            WorkoutScreen(navController=navController)
        }
    }
}

fun NavGraphBuilder.heartRateNavGraph(
    navController: NavHostController,
    onComposing: (TopAppBarNavigationState) -> Unit
) {
    navigation(
        startDestination = Route.HeartRateMeasurementScreen.route,
        route = Graph.HeartMeasurementGraph.route,
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
        composable(route = "${Route.HeartRateReportScreen.route}?userState={userState}&heartRate={heartRate}",
            arguments = listOf(
                navArgument("userState") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("heartRate") {
                    type = NavType.StringType
                    defaultValue = "0"
                }
            )) { backStackEntry ->
            val userState = backStackEntry.arguments?.getString("userState")
            val heartRate = backStackEntry.arguments?.getString("heartRate")
            val viewModel: HeartRateReportViewModel = hiltViewModel()
            HeartRateReportScreen(
                viewModel = viewModel,
                navController = navController,
                onComposing = onComposing,
                rate = heartRate!!,
                userState = userState
            )
        }
    }
}

fun NavGraphBuilder.workoutNavGraph(
    navController: NavHostController,
    onComposing: (TopAppBarNavigationState) -> Unit
) {
    navigation(
        startDestination = Route.WorkoutExerciseScreen.route,
        route = Graph.WorkoutGraph.route,
    )
    {
        composable(route = Route.WorkoutExerciseScreen.route) {
            val viewModel: WorkoutExerciseViewModel = hiltViewModel()
            WorkoutExerciseScreen(navController = navController, viewModel = viewModel)
        }
    }
}