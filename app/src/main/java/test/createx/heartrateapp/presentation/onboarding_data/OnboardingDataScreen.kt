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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.PageIndicator
import test.createx.heartrateapp.presentation.navigation.Route
import test.createx.heartrateapp.presentation.onboarding_data.components.OnboardingDataPage
import test.createx.heartrateapp.presentation.onboarding_data.components.TextInputComponent
import test.createx.heartrateapp.presentation.onboarding_data.components.dropdown_component.ExpandablePickerButton
import test.createx.heartrateapp.presentation.onboarding_data.components.dropdown_component.UnitPicker
import test.createx.heartrateapp.presentation.onboarding_data.components.toggle_input_component.ToggleInputComponent
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.White

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingDataScreen(viewModel: OnboardingDataViewModel, navController: NavController) {

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
            if (pagerState.currentPage > 0) {
                IconButton(
                    onClick = {
                        scopeBack.launch {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage - 1
                            )
                        }
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.return_icon),
                        contentDescription = "Go back",
                        tint = BlackMain
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(24.dp))
            }
            PageIndicator(pageSize = pages.size, selectedPage = pagerState.currentPage)
            TextButton(onClick = {
                viewModel.onEvent(OnboardingEvent.OnboardingSkipped)
                navController.popBackStack()
                navController.navigate(Route.HomeScreen.route)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
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
                            navController.popBackStack()
                            navController.navigate(Route.HomeScreen.route)
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
                enabled = getEnabledStatus(pagerState.currentPage, viewModel),
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
private fun GetInput(index: Int, viewModel: OnboardingDataViewModel) {

    val onClick: (String) -> Unit = { selectedValue ->
        when (index) {
            0 -> viewModel.onNameChange(selectedValue)
            1 -> viewModel.onSexChange(selectedValue)
            2 -> viewModel.onAgeChange(selectedValue)
            4 -> viewModel.onLifestyleChange(selectedValue)
        }
    }

    val onHeightChange: (String) -> Unit = { height ->
        viewModel.onHeightChange(height)
    }

    val onWeightChange: (String) -> Unit = { weight ->
        viewModel.onWeightChange(weight)
    }

    val onUnitsChange: (String) -> Unit = { units ->
        viewModel.onUnitsChange(units)
    }

    when (index) {
        0 -> {
            TextInputComponent(onInput = onClick, text = viewModel.user.value.name, containerColor = White)
        }

        1 -> {
            ToggleInputComponent(
                data = viewModel.pronouns, onClick = onClick, value = viewModel.user.value.sex
            )
        }

        2 -> {
            ToggleInputComponent(
                data = viewModel.age, onClick = onClick, value = viewModel.user.value.age
            )
        }

        3 -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
            ) {

                UnitPicker(
                    shape = RoundedCornerShape(10.dp),
                    value = viewModel.user.value.units,
                    onChange = onUnitsChange,
                    units = viewModel.units
                )

                ExpandablePickerButton(
                    title = "Weight",
                    shape= RoundedCornerShape(10.dp),
                    isVisible = viewModel.isWeightPickerVisible,
                    onToggleVisibility = {
                        viewModel.onToggleWeightVisibility()
                    },
                    items = viewModel.weightsList,
                    onSelect = onWeightChange,
                    value = viewModel.user.value.weight
                )

                ExpandablePickerButton(
                    title = "Height",
                    shape= RoundedCornerShape(10.dp),
                    isVisible = viewModel.isHeightPickerVisible,
                    onToggleVisibility = {
                        viewModel.onToggleHeightVisibility()
                    },
                    items = viewModel.heightsList,
                    onSelect = onHeightChange,
                    value = viewModel.user.value.height
                )
            }
        }

        4 -> {
            ToggleInputComponent(
                data = viewModel.lifestyle,
                onClick = onClick,
                value = viewModel.user.value.lifestyle,
            )
        }
    }
}


private fun getEnabledStatus(index: Int, viewModel: OnboardingDataViewModel): Boolean {
    return when (index) {
        0 -> viewModel.user.value.name.isNotEmpty() && viewModel.user.value.name.matches(Regex("[a-zA-Z0-9]+"))
        1 -> viewModel.user.value.sex.isNotEmpty()
        2 -> viewModel.user.value.age.isNotEmpty()
        3 -> viewModel.user.value.weight.isNotEmpty() || viewModel.user.value.height.isNotEmpty()
        4 -> viewModel.user.value.lifestyle.isNotEmpty()
        else -> false
    }
}

