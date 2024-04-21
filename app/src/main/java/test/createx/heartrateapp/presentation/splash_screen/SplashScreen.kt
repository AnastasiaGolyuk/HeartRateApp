package test.createx.heartrateapp.presentation.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import test.createx.heartrateapp.R
import test.createx.heartrateapp.ui.theme.GreyBg

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBg),
        contentAlignment = Alignment.Center

    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_icon))
        val logoAnimationState =
            animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress }
        )
        if ((logoAnimationState.isAtEnd && logoAnimationState.isPlaying)) {
            navController.popBackStack()
            navController.navigate(route = viewModel.startDestination.value)
        }
    }
}