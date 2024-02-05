package test.createx.heartrateapp.presentation.onboarding_data.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.presentation.onboarding_data.OnboardingDataViewModel
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.White

@Composable
fun ToggleInputComponent(
    data: List<String>,
    dataLabel: String,
    screenViewModel: OnboardingDataViewModel
) {

    var selectedOption by remember {
        when (dataLabel) {
            "age" -> {
                mutableStateOf(screenViewModel.user.value.age)
            }
            "sex" -> {
                mutableStateOf(screenViewModel.user.value.sex)
            }
            else -> {
                mutableStateOf(screenViewModel.user.value.lifestyle)
            }
        }
    }

    LaunchedEffect(selectedOption){
        when (dataLabel) {
            "age" -> {
                screenViewModel.onAgeChange(selectedOption)
            }
            "sex" -> {
                screenViewModel.onSexChange(selectedOption)
            }
            else -> {
                screenViewModel.onLifestyleChange(selectedOption)
            }
        }
    }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(data) { text ->
                Button(
                    onClick = { selectedOption = text },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = if (text == selectedOption) RedBg else White
                    ),
                    border = if (text == selectedOption) BorderStroke(
                        width = 2.dp,
                        color = RedAction
                    ) else null
                ) {
                    Text(
                        text = text, style = MaterialTheme.typography.titleSmall,
                        color = if (text == selectedOption) RedAction else BlackMain,
                    )
                }
            }
            if (screenHeight < 700.dp) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                }
            }
        }
        if (screenHeight < 700.dp) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, GreyBg),
                        )
                    )
            )
        }
    }
}
