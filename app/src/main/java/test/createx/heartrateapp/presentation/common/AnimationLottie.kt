package test.createx.heartrateapp.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimationLottie(modifier: Modifier = Modifier, animationId: Int, contentScale: ContentScale) {

    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            animationId
        )
    )

    LottieAnimation(
        composition = preloaderLottieComposition,
        modifier = modifier,
        alignment = Alignment.Center,
        contentScale = contentScale,
        iterations = LottieConstants.IterateForever,
    )
}