package test.createx.heartrateapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun PeriodSelector(
    periods: List<String>,
    selectedPeriod: String,
    onPeriodChange: (String) -> Unit,
//    isClickable: (String) -> Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(White),
        horizontalArrangement = Arrangement.spacedBy(
            1.dp,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.Top
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        periods.forEach { period ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(28.dp)
                    .padding(top = 4.dp)
                    .background(
                        color = if (selectedPeriod == period) RedBg else White,
                        shape = RoundedCornerShape(9.dp)
                    )
                    .clip(shape = RoundedCornerShape(9.dp))
                    .clickable(onClick = { onPeriodChange(period) }),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = period,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (selectedPeriod == period) RedAction else BlackMain,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}