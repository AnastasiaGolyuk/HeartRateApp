package test.createx.heartrateapp.presentation.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import test.createx.heartrateapp.presentation.bottomNavBar.BottomNavBar
import test.createx.heartrateapp.presentation.navigation.AppState
import test.createx.heartrateapp.presentation.navigation.HomeNavGraph
import test.createx.heartrateapp.presentation.topAppBar.TopAppBar
import test.createx.heartrateapp.ui.theme.White

@Composable
fun HomeScreen(isFirstEnter: Boolean) {

    val homeNavController = rememberNavController()

    val appState = remember {
        AppState(homeNavController)
    }

    val bottomBarOffset: State<Dp> =
        animateDpAsState(
            targetValue = if (appState.shouldShowAppBars) 0.dp else 64.dp,
            animationSpec = tween(durationMillis = 500, easing = LinearEasing),
            label = "bottomBarAnimation"
        )

    var shouldPutOffset by remember {
        mutableStateOf(!isFirstEnter)
    }

    LaunchedEffect(appState.shouldShowAppBars) {
        delay(500)
        shouldPutOffset = true
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
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = if (!shouldPutOffset)
                            paddingValues.calculateBottomPadding()
                        else
                            paddingValues.calculateBottomPadding() - bottomBarOffset.value
                    )
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
                offset = if (!shouldPutOffset) 0.dp else bottomBarOffset.value
            )
        }
    )
}