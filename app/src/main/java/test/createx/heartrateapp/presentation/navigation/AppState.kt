package test.createx.heartrateapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

class AppState(
    private val navController: NavHostController
) {
    private val bottomBarRoutesList = listOf(
        Route.StatisticsScreen,
        Route.WorkoutScreen,
        Route.HeartRateScreen,
        Route.ReportScreen,
        Route.SettingsScreen,
        Route.ProfileScreen
    )
    private val bottomBarRoutes = bottomBarRoutesList.map { it.route }

    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes
}