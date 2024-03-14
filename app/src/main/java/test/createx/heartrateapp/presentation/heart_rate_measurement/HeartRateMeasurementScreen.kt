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
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import co.yml.charts.common.extensions.isNotNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    var userState: String? by remember { mutableStateOf("") }

    val openAlertDialog = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

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
        if (previewView.isNotNull() && !viewModel.areBpmUpdatesInitialized()) {
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

    LaunchedEffect(userState) {
        if (userState != "") {
            showSheet=false
            delay(300)
            navController.popBackStack()
            navController.navigate("${Route.HeartRateReportScreen.route}?userState=${userState}&heartRate=${rate.value}")
        }
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
                    viewModel.disposeSubscription()
                    openAlertDialog.value = false
                    showSheet = false
                    coroutineScope.launch { delay(300) }
                    navController.popBackStack()
                },
                dialogTitle = stringResource(R.string.measurements_alert_dialog_title),
                dialogText = stringResource(R.string.measurements_alert_dialog_description),
                confirmButtonText = stringResource(id = R.string.close_icon_description)
            )
        }
        AnimatedContent(targetState = hint.value, transitionSpec = {
            fadeIn(
                animationSpec = tween(900),
            ) togetherWith fadeOut(
                animationSpec = tween(900)
            )
        }, label = stringResource(R.string.hint_content_animation_label)) { targetState ->
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .animateContentSize(animationSpec = tween(500))
                    .background(color = RedBg, shape = RoundedCornerShape(10.dp)),
            ) {
                Text(
                    modifier = Modifier.weight(1f)
                        .padding(16.dp),
                    text = stringResource(id = targetState.hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = RedMain,
                    textAlign = TextAlign.Start
                )
                if (targetState.image != null) {
                    Image(
                        painter = painterResource(id = targetState.image),
                        contentDescription = stringResource(R.string.hint_image_description),
                        modifier = Modifier.padding(0.dp).height(84.dp),
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
                    positionValue = 100f - (timeLeft.value * 100 / viewModel.fullCycle),
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
                        contentDescription = stringResource(id = R.string.heart_icon_description),
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
                            text = stringResource(id = R.string.bpm_title),
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
                        color = if (timeLeft.value == viewModel.fullCycle) Color.Transparent else if (isPaused.value) RedMain else GreenRateText,
                        shape = RoundedCornerShape(50.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
                ) {
                    Text(
                        text = stringResource(id = R.string.measurement_time_text),
                        style = MaterialTheme.typography.bodySmall,
                        color = BlackMain,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = if (ceil(timeLeft.value) == viewModel.fullCycle) stringResource(
                            R.string.measurement_time_init_text,
                            ceil(timeLeft.value).toInt()
                        ) else stringResource(
                            R.string.measurement_time_left_text, ceil(
                                timeLeft.value
                            ).toInt()
                        ),
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
        StateBottomSheetDialog(
            onShowDialogChange = { showDialog ->
                openAlertDialog.value = showDialog
            }, onCreateReport = { state ->
                userState = state
                showSheet = false
            })
        }
    }
}