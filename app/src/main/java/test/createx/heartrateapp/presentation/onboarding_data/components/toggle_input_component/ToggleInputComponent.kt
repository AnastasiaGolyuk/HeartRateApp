package test.createx.heartrateapp.presentation.onboarding_data.components.toggle_input_component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.ui.theme.GreyBg

@Composable
fun ToggleInputComponent(
    data: List<String>,
    onClick: (String) -> Unit,
    value: String,
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight - 300.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(data) { text ->
                ButtonItem(onClick = onClick, value = value, text = text)
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, GreyBg),
                    )
                )
        )

    }
}
