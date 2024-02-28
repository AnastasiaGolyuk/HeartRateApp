package test.createx.heartrateapp.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.onboarding.components.OnboardingScreenContent
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {

    val pages = Page.get()
    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f
    ) {
        pages.size
    }

    val compositionBg by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.onboarding1))
    val bgAnimationState = animateLottieCompositionAsState(composition = compositionBg)

    var isPlaying by remember { mutableStateOf(false) }

    val compositionBg2 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.onboarding2))
    val bg2AnimationState =
        animateLottieCompositionAsState(composition = compositionBg2, isPlaying = isPlaying)

    var isContentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(3000)
        isContentVisible = true
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.targetPage }.collect { page ->
            if (page == 1) {
                isPlaying = true
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (!isPlaying) {
            LottieAnimation(
                composition = compositionBg,
                progress = { bgAnimationState.progress },
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
        } else {
            LottieAnimation(
                composition = compositionBg2,
                progress = { bg2AnimationState.progress },
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
        }
        OnboardingScreenContent(
            pagerState = pagerState,
            pages = pages,
            navController = navController,
            isContentVisible = isContentVisible
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPrev() {
    HeartRateAppTheme {
        OnboardingScreen(navController = rememberNavController())
    }
}