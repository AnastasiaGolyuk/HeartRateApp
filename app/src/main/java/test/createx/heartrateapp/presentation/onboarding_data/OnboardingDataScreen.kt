package test.createx.heartrateapp.presentation.onboarding_data

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import test.createx.heartrateapp.R
import test.createx.heartrateapp.data.model.DataPage
import test.createx.heartrateapp.presentation.common.PageIndicator
import test.createx.heartrateapp.presentation.onboarding_data.components.OnboardingDataPage
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.White

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingDataScreen(onEvent: (OnboardingEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GreyBg)
    ) {
        val pages = DataPage.get()
        val pagerState = rememberPagerState(
            initialPage = 0, initialPageOffsetFraction = 0f
        ) {
            pages.size
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val scopeBack = rememberCoroutineScope()

            IconButton(
                onClick = {
                    scopeBack.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage - 1
                            )
                        }
                    }
                },

                modifier = if (pagerState.currentPage == 0) {
                    Modifier.alpha(0f)
                } else {
                    Modifier.alpha(1f)
                }

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.return_icon),
                    contentDescription = "Favorite",
                    tint = BlackMain
                )
            }
            PageIndicator(pageSize = pages.size, selectedPage = pagerState.currentPage)
            TextButton(onClick = {
                onEvent(OnboardingEvent.OnboardingCompleted)
            }, content = {
                Text(
                    text = "Skip",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = GreySubText
                )
            })
        }
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            HorizontalPager(state = pagerState) { index ->
                OnboardingDataPage(dataPage = pages[index])
            }
            val scopeContinue = rememberCoroutineScope()
            ElevatedButton(
                onClick = {
                    scopeContinue.launch {
                        if (pagerState.currentPage == pages.size - 1) {
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
                    .padding(bottom = 59.dp)
                    .size(width = 328.dp, height = 48.dp)
                    .shadow(
                        ambientColor = RedMain, spotColor = Color(0xFFCC0909), elevation = 16.dp
                    ),
                colors = ButtonDefaults.elevatedButtonColors(containerColor = RedMain)
            ) {
                Text(
                    text = "Continue",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
        }
    }
}
