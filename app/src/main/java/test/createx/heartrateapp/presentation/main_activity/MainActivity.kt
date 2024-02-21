package test.createx.heartrateapp.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import test.createx.heartrateapp.presentation.navigation.EnteringNavGraph
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.White

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val enteringNavController = rememberNavController()
            HeartRateAppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White)
                ) {
                    EnteringNavGraph(
                        navController = enteringNavController,
                    )
                }
            }
        }
    }
}

