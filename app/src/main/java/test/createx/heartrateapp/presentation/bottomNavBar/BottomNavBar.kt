package test.createx.heartrateapp.presentation.bottomNavBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.navigation.Route
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.White

@Composable
fun BottomNavBar(navController: NavController, offset: Dp) {

    val items = listOf(
        BottomNavBarItem.StatisticsScreen,
        BottomNavBarItem.WorkoutScreen,
        BottomNavBarItem.HeartRateScreen,
        BottomNavBarItem.ReportScreen,
        BottomNavBarItem.SettingsScreen,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = Modifier
            .height(64.dp)
            .offset(y = offset),
        containerColor = White,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { item ->
                IconButton(
                    onClick = {
                        navController.navigate(item.route) {
                            restoreState = true
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            color = if (currentRoute == item.route
                                || (item.route == items.last().route
                                        && currentRoute == Route.ProfileScreen.route)
                            )
                                RedBg
                            else
                                White
                        ),
                ) {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = stringResource(R.string.bottom_nav_bar_icon_description),
                        tint = if (currentRoute == item.route
                            || (item.route == items.last().route
                                    && currentRoute == Route.ProfileScreen.route))
                            RedAction
                        else
                            GreySubText
                    )
                }
            }
        }
    }

}