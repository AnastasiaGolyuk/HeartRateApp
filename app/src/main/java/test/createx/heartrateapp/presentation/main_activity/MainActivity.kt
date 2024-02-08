package test.createx.heartrateapp.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import test.createx.heartrateapp.presentation.bottomNavBar.BottomNavBar
import test.createx.heartrateapp.presentation.navigation.AppState
import test.createx.heartrateapp.presentation.navigation.NavGraph
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
            val appState = AppState(navController)
            HeartRateAppTheme {
                Scaffold(
                    content = { paddingValues ->
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .background(color = White)
                        ) {
                            NavGraph(
                                startDestination = mainViewModel.startDestination.value,
                                navController = navController
                            )
                        }
                    },
                    bottomBar = {
                        if (appState.shouldShowBottomBar) {
                            BottomNavBar(navController = navController)
                        }
                    }
                )
            }
        }
    }
}
