package test.createx.heartrateapp.presentation.onboarding_data

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import test.createx.heartrateapp.R
import test.createx.heartrateapp.domain.model.DataPage
import test.createx.heartrateapp.presentation.common.PageIndicator
import test.createx.heartrateapp.presentation.onboarding_data.components.OnboardingDataPage
import test.createx.heartrateapp.presentation.onboarding_data.components.TextInputComponent
import test.createx.heartrateapp.presentation.onboarding_data.components.ToggleInputComponent
import test.createx.heartrateapp.presentation.onboarding_data.components.dropdown_component.DropDownMenuComponent
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.White

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun OnboardingDataScreen(viewModel: OnboardingDataViewModel) {



    val pages = DataPage.get()
    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f
    ) {
        pages.size
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != 0) {
            keyboardController?.hide()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GreyBg)
    ) {
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
                viewModel.onEvent(OnboardingEvent.OnboardingCompleted)
            }, content = {
                Text(
                    text = "Skip",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = GreySubText
                )
            })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box {
            HorizontalPager(state = pagerState, userScrollEnabled = false) { index ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)) {
                    OnboardingDataPage(dataPage = pages[index])
                    GetInput(index = index, viewModel = viewModel)
                }
            }
            val scopeContinue = rememberCoroutineScope()
            ElevatedButton(
                onClick = {
                    scopeContinue.launch {
                        if (pagerState.currentPage == pages.size - 1) {
                            viewModel.onEvent(OnboardingEvent.OnboardingCompleted)
                        } else {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1
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
                enabled= getEnabledStatus(pagerState.currentPage,viewModel),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = RedMain,
                    disabledContainerColor = RedMain.copy(alpha = 0.5f),
                    disabledContentColor = RedMain.copy(alpha = 0.5f),
                )
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


@Composable
fun GetInput(index: Int, viewModel: OnboardingDataViewModel) {
    val pronouns = listOf("She / Her", "He / Him", "They / Them")
    val age = listOf("Less than 20", "20-29", "30-39", "40-49", "50-59", "More than 60")
    val lifestyle = listOf("Active", "Moderate", "Sedentary")
    when (index) {
        0 -> {
            TextInputComponent(viewModel)
        }

        1 -> {
            ToggleInputComponent(data = pronouns, dataLabel = "sex", screenViewModel = viewModel)
        }

        2 -> {
            ToggleInputComponent(data = age, dataLabel = "age", screenViewModel = viewModel)
        }

        3 -> {
            DropDownMenuComponent(screenViewModel = viewModel)
        }

        4 -> {
            ToggleInputComponent(
                data = lifestyle,
                dataLabel = "lifestyle",
                screenViewModel = viewModel
            )
        }
    }
}

fun getEnabledStatus(index: Int, viewModel: OnboardingDataViewModel): Boolean {
    return when (index) {
        0 -> viewModel.user.value.name.isNotEmpty()
        1 -> viewModel.user.value.sex.isNotEmpty()
        2 -> viewModel.user.value.age.isNotEmpty()
        3 -> viewModel.user.value.weight.isNotEmpty() || viewModel.user.value.height.isNotEmpty()
        4 -> viewModel.user.value.lifestyle.isNotEmpty()
        else -> false
    }
}

