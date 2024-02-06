package test.createx.heartrateapp.presentation.onboarding_data.components.toggle_input_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.White

@Composable
fun ButtonItem(
    onClick: (String) -> Unit,
    value: String,
    text: String
) {
    Button(
        onClick = {
            onClick(text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = if (text == value) RedBg else White
        ),
        border = if (text == value)
            BorderStroke(
                width = 2.dp,
                color = RedAction
            ) else null
    ) {
        Text(
            text = text, style = MaterialTheme.typography.titleSmall,
            color = if (text == value) RedAction else BlackMain,
        )
    }
}