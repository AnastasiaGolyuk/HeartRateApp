package test.createx.heartrateapp.presentation.workout_exercises

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.AlertDialog
import test.createx.heartrateapp.presentation.common.LinearProgressIndicator
import test.createx.heartrateapp.presentation.common.PageIndicator
import test.createx.heartrateapp.presentation.navigation.Route
import test.createx.heartrateapp.presentation.onboarding_data.OnboardingEvent
import test.createx.heartrateapp.presentation.topAppBar.TopAppBarNavigationState
import test.createx.heartrateapp.presentation.workout_exercises.components.WorkoutExercisePage
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyProgressbar
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.RedProgressbar
import test.createx.heartrateapp.ui.theme.White
import kotlin.math.ceil

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkoutExerciseScreen(navController: NavController, viewModel: WorkoutExerciseViewModel) {

    val pages = Workout.get()
    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f
    ) {
        pages.size
    }

    val timeLeft = viewModel.timeLeft

    val isPaused = viewModel.isPaused

    val openAlertStopDialog = remember { mutableStateOf(false) }

    val openAlertBackDialog = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val backButtonAlpha: State<Float> =
        animateFloatAsState(
            targetValue = if (pagerState.currentPage > 0) 1f else 0f,
            animationSpec = tween(1000),
            label = "backButtonAnimation"
        )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        if (openAlertBackDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.startExercise()
                    viewModel.startMeasurement()
                    openAlertBackDialog.value = false
                },
                onConfirmation = {
                    viewModel.restartTimer()
                    openAlertBackDialog.value = false
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage - 1,
                            animationSpec = tween(1800)
                        )
                    }
                },
                dialogTitle = "Return to the previous exercise?",
                confirmButtonText = "Return"
            )
        }
        if (openAlertStopDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.startExercise()
                    viewModel.startMeasurement()
                    openAlertStopDialog.value = false
                },
                onConfirmation = {
                    openAlertStopDialog.value = false
                    coroutineScope.launch {
                        delay(200)
                        navController.navigate(Route.WorkoutScreen.route)
                    }
                },
                dialogTitle = "Stop the workout?",
                dialogText = "Progress will be reset, all workout will begin again",
                confirmButtonText = "Stop"
            )
        }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(color = White),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                IconButton(
                    onClick = {
                        openAlertBackDialog.value = true
                        viewModel.pauseMeasurement()
                    },
                    modifier = Modifier.alpha(backButtonAlpha.value),
                    enabled = pagerState.currentPage > 0,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.return_icon),
                        contentDescription = "Go back",
                        tint = BlackMain
                    )
                }

                PageIndicator(pageSize = pages.size, selectedPage = pagerState.currentPage)
                TextButton(onClick = {
                    openAlertStopDialog.value = true
                    viewModel.pauseMeasurement()
                }, content = {
                    Text(
                        text = "Stop",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = GreySubText
                    )
                })
            }
            HorizontalPager(state = pagerState, userScrollEnabled = false) { index ->
                WorkoutExercisePage(workout = pages[index])
            }
        }
        Column(
            modifier = Modifier
                .width(328.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    4.dp,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${
                        ceil(
                            timeLeft.value
                        ).toInt()
                    }",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = BlackMain
                )
                Text(
                    text = "sec to complete",
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreySubText
                )
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp),
                progress = 1 - timeLeft.value / viewModel.fullCycle,
                progressColor = RedProgressbar,
                lineColor = GreyProgressbar,
                clipShape = RoundedCornerShape(9.dp)
            )
        }

        val scope = rememberCoroutineScope()
        ElevatedButton(
            onClick = {
                if (isPaused.value) {
                    viewModel.startExercise()
                    viewModel.startMeasurement()
                } else if (timeLeft.value <= 0) {
                    viewModel.restartTimer()
                    scope.launch {
                        if (pagerState.currentPage == 4) {
                            navController.navigate(Route.WorkoutScreen.route)
                        } else {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1,
                                animationSpec = tween(1800)
                            )
                        }
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
            enabled = (isPaused.value && timeLeft.value == viewModel.fullCycle) || timeLeft.value <= 0,

            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = RedMain,
                disabledContainerColor = RedMain.copy(alpha = 0.5f),
                disabledContentColor = RedMain.copy(alpha = 0.5f),
            )
        ) {
            Text(
                text = if (isPaused.value && timeLeft.value == viewModel.fullCycle) "Start exercise" else "Next exercise",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        }
    }
}
