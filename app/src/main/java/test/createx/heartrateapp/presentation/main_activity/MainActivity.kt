package test.createx.heartrateapp.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import test.createx.heartrateapp.presentation.bottomNavBar.BottomNavBar
import test.createx.heartrateapp.presentation.navigation.AppState
import test.createx.heartrateapp.presentation.navigation.NavGraph
import test.createx.heartrateapp.presentation.topAppBar.TopAppBar
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.White

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = {
                mainViewModel.splashCondition.value
            })
        }
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val appState = remember {
                AppState(navController)
            }
            HeartRateAppTheme {
                Scaffold(
                    topBar = {
                        if (appState.shouldShowAppBars) {
                            TopAppBar(
                                title = appState.getTopBarTitle(currentRoute!!),
                                shouldShowNavigationButton = appState.shouldShowTopAppBarIcon,
                                iconRes = appState.topAppBarNavigationState.value.iconRes,
                                action = appState.topAppBarNavigationState.value.action
                            )
                        }
                    },
                    content = { paddingValues ->
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .background(color = White)
                        ) {
                            NavGraph(
                                startDestination = mainViewModel.startDestination.value,
                                navController = navController,
                                onComposing = { barState -> appState.onTopAppBarNavStateChange(barState) }
                            )
                        }
                    },
                    bottomBar = {
                        if (appState.shouldShowAppBars) {
                            BottomNavBar(navController = navController)
                        }
                    }
                )
            }
        }
    }
}

