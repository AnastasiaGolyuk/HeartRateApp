package test.createx.heartrateapp.presentation.heart_rate

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.CircularProgressIndicator
import test.createx.heartrateapp.presentation.heart_rate.components.HintBottomSheetDialog
import test.createx.heartrateapp.presentation.heart_rate.components.StateBottomSheetDialog
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreenRateText
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.GreyProgressbar
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.RedProgressbar
import test.createx.heartrateapp.ui.theme.White
import kotlin.math.ceil

@Composable
fun HeartRateScreen(
//    viewModel: HeartRateViewModel
) {

//    val heartRateState = rememberHeartRateSensorState()

    val hint by remember {
        mutableStateOf("Relax, don't control your breathing. Press the button to start the measurement")
    }

    var rate by remember {
        mutableStateOf("--")
    }

    var showSheet by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(30f) }
    var isPaused by remember { mutableStateOf(true) }

    LaunchedEffect(timeLeft,isPaused) {
        while (timeLeft > 0 && !isPaused) {
            delay(100L)
            timeLeft -= 0.1f
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
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
                    text = hint,
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
                    positionValue = 100f - (timeLeft * 100f / 30f),
                    primaryColor = RedProgressbar,
                    secondaryColor = GreyProgressbar,
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.fillMaxHeight(0.12f))
                    Image(
                        painter = painterResource(id = R.drawable.heart_rate),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize(0.34f)
                    )
                    Spacer(modifier = Modifier.fillMaxHeight(0.08f))
                    Text(
                        text = rate,
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
            Spacer(modifier = Modifier.fillMaxHeight(0.06f))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        color = GreyBg,
                    )
                    .border(
                        width = 2.dp,
                        color = if (timeLeft == 30f) Color.Transparent else if (isPaused) RedMain else GreenRateText,
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
                        text = if (ceil(timeLeft) == 30f) "Just ${ceil(timeLeft).toInt()} seconds" else "${
                            ceil(
                                timeLeft
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
                showSheet = true
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
                isPaused = false
            })
        }
        if (timeLeft<=0f) {
            StateBottomSheetDialog (onDismiss = {
                isPaused = true
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeartRateScreenPreview() {
    HeartRateAppTheme {
        HeartRateScreen()
    }
}