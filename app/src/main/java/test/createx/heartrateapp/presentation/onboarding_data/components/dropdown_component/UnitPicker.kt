package test.createx.heartrateapp.presentation.onboarding_data.components.dropdown_component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.White

@Composable
fun UnitPicker(
    value: String,
    onChange: (String) -> Unit,
    units: List<String>
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                style = MaterialTheme.typography.titleSmall,
                color = BlackMain,
                text = "Units",
                textAlign = TextAlign.Center
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    0.dp,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(28.dp)
                    .padding(vertical = 0.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = RedBg, shape = RoundedCornerShape(10.dp))
            ) {
                units.forEach { text ->
                    Text(
                        text = text,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {
                                onChange(text)
                            }
                            .background(if (text == value) RedBg else White)
                            .padding(
                                horizontal = 10.dp,
                                vertical = 4.dp
                            ),
                        style = MaterialTheme.typography.bodySmall,
                        color = if (text == value) RedAction else BlackMain,
                    )
                }
            }
        }
    }
}