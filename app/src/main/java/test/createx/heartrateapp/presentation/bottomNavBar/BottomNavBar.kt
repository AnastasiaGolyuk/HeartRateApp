package test.createx.heartrateapp.presentation.bottomNavBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.White

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavBarItem.StatisticsScreen,
        BottomNavBarItem.WorkoutScreen,
        BottomNavBarItem.HeartRateScreen,
        BottomNavBarItem.ReportScreen,
        BottomNavBarItem.SettingsScreen
    )
    BottomAppBar(
        modifier = Modifier.height(64.dp),
        backgroundColor = White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = if (currentRoute == item.route) RedBg else White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = "",
                            tint = if (currentRoute == item.route) RedAction else GreySubText
                        )
                    }
                },
                selected = currentRoute == item.route,
                selectedContentColor = RedBg,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}