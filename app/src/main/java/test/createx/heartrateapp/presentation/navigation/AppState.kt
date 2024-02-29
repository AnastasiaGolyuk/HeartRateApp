package test.createx.heartrateapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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
        Route.ProfileScreen.route
    )
    private val appBarIconRoutes = appBarIconRoutesList.map { it }

    val shouldShowAppBars: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in appBarsRoutes

    val shouldShowTopAppBarIcon: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in appBarIconRoutes


    val topBarTitle: String
        @Composable get() {
            return when (navController.currentBackStackEntryAsState().value?.destination?.route) {
                Route.StatisticsScreen.route -> "My statistics"
                Route.WorkoutScreen.route -> "Workout"
                Route.HeartRateScreen.route -> "Heart rate"
                Route.HeartRateMeasurementScreen.route -> "Heart rate"
                "${Route.HeartRateReportScreen.route}?userState={userState}&heartRate={heartRate}" -> "HeartRate"
                Route.ReportScreen.route -> "My reports"
                Route.SettingsScreen.route -> "Settings"
                Route.ProfileScreen.route -> "Profile details"
                else -> ""
            }
        }


    private val _topAppBarNavigationState = mutableStateOf(TopAppBarNavigationState())
    val topAppBarNavigationState: State<TopAppBarNavigationState> = _topAppBarNavigationState

    fun onTopAppBarNavStateChange(topAppBarNavigationStateNew: TopAppBarNavigationState){
        _topAppBarNavigationState.value=topAppBarNavigationStateNew
    }
}