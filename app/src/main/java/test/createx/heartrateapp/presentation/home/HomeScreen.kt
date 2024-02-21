package test.createx.heartrateapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import test.createx.heartrateapp.presentation.bottomNavBar.BottomNavBar
import test.createx.heartrateapp.presentation.navigation.AppState
import test.createx.heartrateapp.presentation.navigation.HomeNavGraph
import test.createx.heartrateapp.presentation.topAppBar.TopAppBar
import test.createx.heartrateapp.ui.theme.White

@Composable
fun HomeScreen() {

    val homeNavController = rememberNavController()

    val appState = remember {
        AppState(homeNavController)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = appState.topBarTitle,
                shouldShowNavigationButton = appState.shouldShowTopAppBarIcon,
                iconRes = appState.topAppBarNavigationState.value.iconRes,
                action = appState.topAppBarNavigationState.value.action
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(color = White)
            ) {
                HomeNavGraph(
                    navController = homeNavController,
                    onComposing = { barState -> appState.onTopAppBarNavStateChange(barState) }
                )
            }
        },
        bottomBar = {
            BottomNavBar(
                navController = homeNavController,
            )
        }
    )
}