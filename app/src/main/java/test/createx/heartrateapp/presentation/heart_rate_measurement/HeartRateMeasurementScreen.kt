package test.createx.heartrateapp.presentation.heart_rate_measurement

import android.view.SurfaceView
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.airbnb.lottie.parser.IntegerParser
import kotlinx.coroutines.delay
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.AlertDialog
import test.createx.heartrateapp.presentation.common.AnimationLottie
import test.createx.heartrateapp.presentation.common.CircularProgressIndicator
import test.createx.heartrateapp.presentation.heart_rate_measurement.components.StateBottomSheetDialog
import test.createx.heartrateapp.presentation.navigation.Route
import test.createx.heartrateapp.presentation.topAppBar.TopAppBarNavigationState
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreenRateText
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.GreyProgressbar
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.RedProgressbar
import test.createx.heartrateapp.ui.theme.White
import kotlin.math.ceil

@Composable
fun HeartRateMeasurementScreen(
    viewModel: HeartRateMeasurementViewModel, onComposing: (TopAppBarNavigationState) -> Unit,
    navController: NavController
) {

    val rate = viewModel.rate

    val hint = viewModel.hint

    var showSheet by remember { mutableStateOf(false) }

    val timeLeft = viewModel.timeLeft

    val isPaused = viewModel.isPaused

    var previewView: SurfaceView? by remember {
        mutableStateOf(null)
    }

    val openAlertDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onComposing(
            TopAppBarNavigationState(
                action = {
                    openAlertDialog.value = true
                },
                iconRes = R.drawable.close_icon
            )
        )
    }

    LaunchedEffect(previewView) {
        if (previewView != null && !viewModel.areBpmUpdatesInited()) {
            delay(800)
            viewModel.initBpmUpdates(previewView!!)
        }
    }

    LaunchedEffect(timeLeft.value) {
        if (timeLeft.value <= 0) {
            showSheet = true
        }
    }

    LaunchedEffect(isPaused.value) {
        viewModel.startMeasurement()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.disposeSubscription()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Box(modifier = Modifier.size(1.dp)) {
            AndroidView(
                factory = { context ->
                    SurfaceView(context).apply {
                        this.clipToOutline = true
                        previewView = this
                    }
                },
                modifier = Modifier.matchParentSize(),
                update = { previewView = it }
            )
        }
        if (openAlertDialog.value) {
            AlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    viewModel.disposeSubscription()
                    navController.popBackStack()
                },
                dialogTitle = "Close the measurement?",
                dialogText = "The measured data will not be saved",
                confirmButtonText = "Close"
            )
        }
        AnimatedContent(targetState = hint.value, transitionSpec = {
            fadeIn(
                animationSpec = tween(900),
            ) togetherWith fadeOut(
                animationSpec = tween(900)
            )
        }, label = "hintContentAnimation") { targetState ->
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .animateContentSize(animationSpec = tween(500))
                    .background(color = RedBg, shape = RoundedCornerShape(10.dp)),
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    text = targetState.hint,
                    style = MaterialTheme.typography.bodyMedium,
                    color = RedMain,
                    textAlign = TextAlign.Start
                )
                if (targetState.image != null) {
                    Image(
                        painter = painterResource(id = targetState.image),
                        contentDescription = "",
                        modifier = Modifier.padding(0.dp),
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 42.dp)
                    .fillMaxHeight(0.45f)
                    .aspectRatio(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize(),
                    positionValue = 100f - (timeLeft.value * 100 / 30f),
                    primaryColor = RedProgressbar,
                    secondaryColor = GreyProgressbar,
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.heart_rate),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize(0.34f)
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = rate.value,
                            style = MaterialTheme.typography.titleLarge,
                            color = BlackMain,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "bmp",
                            style = MaterialTheme.typography.bodyMedium,
                            color = GreySubText,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.06f))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        color = GreyBg,
                    )
                    .border(
                        width = 2.dp,
                        color = if (timeLeft.value == 30f) Color.Transparent else if (isPaused.value) RedMain else GreenRateText,
                        shape = RoundedCornerShape(50.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
                ) {
                    Text(
                        text = "Measurement time:",
                        style = MaterialTheme.typography.bodySmall,
                        color = BlackMain,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = if (ceil(timeLeft.value) == 30f) "Just ${ceil(timeLeft.value).toInt()} seconds" else "${
                            ceil(
                                timeLeft.value
                            ).toInt()
                        } seconds left",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = RedMain,
                        textAlign = TextAlign.Center
                    )

                }
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            ) {
                AnimationLottie(
                    animationId = R.raw.pulse_indicator,
                    contentScale = ContentScale.FillWidth,
                    isPlaying = !isPaused.value
                )
            }
        }
        if (showSheet) {
            StateBottomSheetDialog(onDismiss = {
                showSheet = false
            }, onCreateReport = { userState ->
                navController.popBackStack()
                navController.navigate("${Route.HeartRateReportScreen.route}?userState=${userState}&heartRate=${rate.value}")
            })
        }
    }
}