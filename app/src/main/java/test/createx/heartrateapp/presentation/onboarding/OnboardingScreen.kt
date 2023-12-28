package test.createx.heartrateapp.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import test.createx.heartrateapp.presentation.common.PageIndicator
import test.createx.heartrateapp.presentation.onboarding.components.OnboardingPage
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.RedMain

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onEvent: (OnboardingEvent) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        val pages = Page.get()
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) {
            pages.size
        }
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, bottom = if (screenHeight < 700.dp) 25.dp else 50.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            PageIndicator(pageSize = pages.size, selectedPage = pagerState.currentPage)
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(state = pagerState) { index ->
                OnboardingPage(page = pages[index])
            }
            if (screenHeight < 700.dp) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, RedBg),
                            )
                        )
                )
            }
            val scope = rememberCoroutineScope()
            ElevatedButton(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage == 1) {
                            onEvent(OnboardingEvent.OnboardingCompleted)
                        } else {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1
                            )
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 58.dp)
                    .size(width = 328.dp, height = 48.dp)
                    .shadow(
                        ambientColor = RedMain,
                        spotColor = Color(0xFFCC0909),
                        elevation = 16.dp
                    ),
                colors = ButtonDefaults.elevatedButtonColors(containerColor = RedMain)
            ) {
                Text(
                    text = if (pagerState.currentPage == 0) "Continue" else "Get started",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
        }
    }
}