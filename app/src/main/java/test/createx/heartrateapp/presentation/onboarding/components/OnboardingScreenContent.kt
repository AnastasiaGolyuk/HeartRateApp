package test.createx.heartrateapp.presentation.onboarding.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import test.createx.heartrateapp.presentation.common.PageIndicator
import test.createx.heartrateapp.presentation.navigation.Route
import test.createx.heartrateapp.presentation.onboarding.Page
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.RedMain

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreenContent(
    pagerState: PagerState,
    pages: List<Page>,
    navController: NavController,
    isContentVisible: Boolean
) {
    AnimatedVisibility(
        visible = isContentVisible,
        enter = fadeIn(animationSpec = tween(2200)),
        exit = fadeOut(animationSpec = tween(2200))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 25.dp
                    ),
                horizontalArrangement = Arrangement.Center
            ) {
                PageIndicator(pageSize = pages.size, selectedPage = pagerState.currentPage)
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                HorizontalPager(state = pagerState, userScrollEnabled = false) { index ->
                    OnboardingPage(page = pages[index])
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, RedBg),
                            )
                        )
                )

                val scope = rememberCoroutineScope()
                ElevatedButton(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 1) {
                                navController.navigate(Route.PaywallScreen.route)
                            } else {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1,
                                    animationSpec = tween(1800)
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 35.dp)
                        .size(width = 328.dp, height = 48.dp)
                        .shadow(
                            elevation = 18.dp,
                            shape = RoundedCornerShape(50.dp),
                            clip = true,
                            ambientColor = Color(0xFFCC0909),
                            spotColor = Color(0xFFCC0909),
                        )
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = RedMain,
                        disabledContainerColor = RedMain.copy(alpha = 0.5f),
                        disabledContentColor = RedMain.copy(alpha = 0.5f),
                    )
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
}