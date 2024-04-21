package test.createx.heartrateapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.topAppBar.TopAppBarNavigationState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppState @Inject constructor(
    private val navController: NavHostController
) {
    private val appBarsRoutesList = listOf(
        Route.StatisticsScreen,
        Route.WorkoutScreen,
        Route.HeartRateScreen,
        Route.ReportScreen,
        Route.SettingsScreen,
        Route.ProfileScreen
    )
    private val appBarsRoutes = appBarsRoutesList.map { it.route }

    private val appBarIconRoutesList = listOf(
        Route.HeartRateMeasurementScreen.route,
        "${Route.HeartRateReportScreen.route}?userState={userState}&heartRate={heartRate}",
        Route.ProfileScreen.route,
    )
    private val appBarIconRoutes = appBarIconRoutesList.map { it }

    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in appBarsRoutes

    val shouldShowTopBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route != Route.WorkoutExerciseScreen.route

    val shouldShowTopAppBarIcon: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in appBarIconRoutes


    val topBarTitleRes: Int
        @Composable get() {
            return when (navController.currentBackStackEntryAsState().value?.destination?.route) {
                Route.StatisticsScreen.route -> R.string.statistics_screen_title
                Route.WorkoutScreen.route -> R.string.workout_screen_title
                Route.HeartRateScreen.route -> R.string.heart_rate_screen_title
                Route.HeartRateMeasurementScreen.route -> R.string.heart_rate_screen_title
                "${Route.HeartRateReportScreen.route}?userState={userState}&heartRate={heartRate}" -> R.string.heart_rate_screen_title
                Route.ReportScreen.route -> R.string.reports_screen_title
                Route.SettingsScreen.route -> R.string.settings_screen_title
                Route.ProfileScreen.route -> R.string.profile_details_screen_title
                else -> R.string.heart_rate_screen_title
            }
        }


    private val _topAppBarNavigationState = mutableStateOf(TopAppBarNavigationState())
    val topAppBarNavigationState: State<TopAppBarNavigationState> = _topAppBarNavigationState

    fun onTopAppBarNavStateChange(topAppBarNavigationStateNew: TopAppBarNavigationState) {
        _topAppBarNavigationState.value = topAppBarNavigationStateNew
    }
}