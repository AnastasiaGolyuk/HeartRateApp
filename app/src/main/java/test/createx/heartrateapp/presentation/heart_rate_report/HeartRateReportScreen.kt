package test.createx.heartrateapp.presentation.heart_rate_report

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.AlertDialog
import test.createx.heartrateapp.presentation.heart_rate_measurement.UserState
import test.createx.heartrateapp.presentation.navigation.Route
import test.createx.heartrateapp.presentation.topAppBar.TopAppBarNavigationState
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.White
import java.time.format.DateTimeFormatter

@Composable
fun HeartRateReportScreen(
    viewModel: HeartRateReportViewModel,
    navController: NavController,
    onComposing: (TopAppBarNavigationState) -> Unit,
    rate: String,
    userState: String?
) {
    val dateTime = viewModel.heartRate.value.dateTime
    val dateString = dateTime.format(DateTimeFormatter.ofPattern("MMM d, HH:mm"))

    val normalHints = listOf(
        stringResource(id = R.string.normal_low_rate_hint),
        stringResource(id = R.string.normal_normal_rate_hint),
        stringResource(id = R.string.normal_high_rate_hint)
    )
    val exerciseHints = listOf(
        stringResource(id = R.string.exercise_low_rate_hint),
        stringResource(id = R.string.exercise_normal_rate_hint),
        stringResource(id = R.string.exercise_high_rate_hint)
    )

    val userStatesList = UserState.get().map { stringResource(id = it.title) }

    val heartRatesMap = HeartRateStatus.get().associate { Pair(it.title, stringResource(id = it.title)) }

    val scrollState = rememberScrollState()

    val openAlertDialog = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        viewModel.initHintsList(normalHints, exerciseHints)
        viewModel.setHeartRateState(
            heartRateValue = rate.toInt(),
            userState = userState,
            userStatesList = userStatesList,
            heartRateStatuses = HeartRateStatus.get(),
            heartRatesMap = heartRatesMap
        )

        onComposing(
            TopAppBarNavigationState(
                action = {
                    openAlertDialog.value = true
                },
                iconRes = R.drawable.close_icon
            )
        )
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
                    openAlertDialog.value = false
                    navController.popBackStack()
                    navController.navigate(Route.HeartRateScreen.route)
                },
                dialogTitle = stringResource(R.string.rate_report_alert_dialog_title),
                dialogText = stringResource(R.string.rate_report_alert_dialog_description),
                confirmButtonText = stringResource(R.string.close_icon_description)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = dateString,
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = GreySubText,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(modifier = Modifier.height(160.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.heart_result_img),
                    contentDescription = stringResource(id = R.string.measurements_heart_image_description),
                    modifier = Modifier.fillMaxHeight(),
                    contentScale = ContentScale.FillHeight
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.background(
                        viewModel.heartRateStatus.value!!.colorBg,
                        RoundedCornerShape(10.dp)
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = viewModel.heartRateStatus.value!!.title),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = viewModel.heartRateStatus.value!!.colorText,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = rate,
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
            Spacer(modifier = Modifier.height(24.dp))
            if (userState != null) {
                Row(
                    modifier = Modifier.background(GreyBg, RoundedCornerShape(50.dp)),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                            .height(24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = getImageResState(userState)),
                            contentDescription = stringResource(id = R.string.user_state_icon_description),
                            modifier = Modifier.size(24.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Text(
                        text = userState,
                        modifier = Modifier.padding(end = 16.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = BlackMain,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
            }
            Box(
                modifier = Modifier.background(RedBg, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = viewModel.heartRateHint.value,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreySubText,
                    textAlign = TextAlign.Start
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        0f to Color.Transparent, 0.25f to White
                    )
                )
        )
        ElevatedButton(
            onClick = {
                viewModel.saveUserHeartRate()
                coroutineScope.launch {
                    delay(500)
                }
                navController.popBackStack()
                navController.navigate(Route.ReportScreen.route)
            },
            modifier = Modifier
                .padding(bottom = 50.dp)
                .size(width = 328.dp, height = 48.dp)
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
                text = stringResource(R.string.save_result_button_text),
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        }
    }
}

@Composable
private fun getImageResState(state: String): Int {
    for (userState in UserState.get()) {
        if (stringResource(id = userState.title) == state) {
            return userState.image
        }
    }
    return 0
}