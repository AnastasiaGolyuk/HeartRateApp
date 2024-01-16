package test.createx.heartrateapp.presentation.onboarding_data.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.White

@Composable
fun ToggleInputComponent(data: List<String>) {

    var selectedOption by remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        data.forEach { text ->
            Button(
                onClick = { onSelectionChange(text) },
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
    }
}

@Preview
@Composable
fun ToggleInputComponentPrev() {
    val pronouns = listOf("She / Her", "He / Him", "They / Them")
    HeartRateAppTheme {
        ToggleInputComponent(pronouns)
    }
}