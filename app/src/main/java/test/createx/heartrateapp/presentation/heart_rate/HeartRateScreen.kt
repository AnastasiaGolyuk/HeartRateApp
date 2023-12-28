package test.createx.heartrateapp.presentation.heart_rate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme

@Composable
fun HeartRateScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Heart Rate")
    }
}

@Preview(showBackground = true)
@Composable
fun HeartRateScreenPreview() {
    HeartRateAppTheme {
        HeartRateScreen()
    }
}