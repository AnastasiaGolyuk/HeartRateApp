package test.createx.heartrateapp.presentation.bottomNavBar

import androidx.annotation.DrawableRes
import test.createx.heartrateapp.R

sealed class BottomNavBarItem(
    @DrawableRes val icon: Int,
    val route: String,
) {

    data object StatisticsScreen : BottomNavBarItem(R.drawable.chart_icon, "statisticsScreen")
    data object WorkoutScreen : BottomNavBarItem(R.drawable.workout_icon, "workoutScreen")
    data object HeartRateScreen : BottomNavBarItem(R.drawable.scan_icon, "heartRateScreen")
    data object ReportScreen : BottomNavBarItem(R.drawable.report_icon, "reportScreen")
    data object SettingsScreen : BottomNavBarItem(R.drawable.profile_icon, "settingsScreen")
}