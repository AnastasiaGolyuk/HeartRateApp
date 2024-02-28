package test.createx.heartrateapp.presentation.heart_rate

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.SurfaceView
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.AlertDialog
import test.createx.heartrateapp.presentation.common.CircularProgressIndicator
import test.createx.heartrateapp.presentation.heart_rate.components.HintBottomSheetDialog
import test.createx.heartrateapp.presentation.heart_rate.components.StateBottomSheetDialog
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
fun HeartRateScreen(
    viewModel: HeartRateViewModel,
    onComposing: (TopAppBarNavigationState) -> Unit,
) {

    val rate = viewModel.rate

    val hint = viewModel.hint

    var showSheet by remember { mutableStateOf(false) }

    var showSheet2 by remember { mutableStateOf(false) }

    val timeLeft = viewModel.timeLeft

    val isPaused = viewModel.isPaused

    val context = LocalContext.current

    var previewView: SurfaceView? by remember {
        mutableStateOf(null)
    }

    val permission = Manifest.permission.CAMERA
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        showSheet = isGranted
    }

    val openAlertDialog = remember { mutableStateOf(false) }

    LaunchedEffect(timeLeft.value) {
        if (timeLeft.value <= 0) {
            showSheet2 = true
        }
    }

    LaunchedEffect(isPaused.value) {
        viewModel.startMeasurement()
        onComposing(
            TopAppBarNavigationState(
                action = {
                    openAlertDialog.value = true
                },
                iconRes = R.drawable.close_icon
            )
        )
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
        if (openAlertDialog.value) {
            AlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    viewModel.flushMeasurementData()
                    openAlertDialog.value = false
                },
                dialogTitle = "Close the measurement?",
                dialogText = "The measured data will not be saved",
                confirmButtonText = "Close"
            )
        }
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .background(color = RedBg, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.TopCenter,
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    text = hint.value.hint,
                    style = MaterialTheme.typography.bodyMedium,
                    color = RedMain,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
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
                    positionValue = 100f - (timeLeft.value * 100f / 30f),
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
        }
        ElevatedButton(
            onClick = {
                checkAndRequestCameraPermission(
                    context = context,
                    permission = permission,
                    onPermissionGranted = { isGranted -> showSheet = isGranted },
                    launcher = launcher
                )
            },
            modifier = Modifier
                .padding(bottom = 50.dp)
                .height(48.dp)
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(50.dp),
                    clip = true,
                    ambientColor = Color(0xFFCC0909),
                    spotColor = Color(0xFFCC0909),
                ),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = RedMain,
                disabledContainerColor = RedMain.copy(alpha = 0.5f),
                disabledContentColor = RedMain.copy(alpha = 0.5f),
            )
        ) {
            Text(
                text = "Start measurement",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        }

        if (showSheet) {
            HintBottomSheetDialog(onDismiss = {
                showSheet = false
                viewModel.initBpmUpdates(previewView!!)
            })
        }

        if (showSheet2) {
            StateBottomSheetDialog(onDismiss = {
                showSheet2 = false
            })
        }
    }
}

fun checkAndRequestCameraPermission(
    context: Context,
    permission: String,
    onPermissionGranted: (Boolean) -> Unit,
    launcher: ManagedActivityResultLauncher<String, Boolean>
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        onPermissionGranted(true)
    } else {
        launcher.launch(permission)
    }
}
