package test.createx.heartrateapp.presentation.statistics.components.averageRateChart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.statistics.DailyAverageMeasurements
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreenRateBg
import test.createx.heartrateapp.ui.theme.GreenRateText
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.RedRateBg
import test.createx.heartrateapp.ui.theme.YellowRateBg
import test.createx.heartrateapp.ui.theme.YellowRateText

@Composable
fun PeakRateValues(dailyAverageMeasurements: DailyAverageMeasurements) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .background(color = YellowRateBg, shape = RoundedCornerShape(10.dp))
                .padding(vertical = 8.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.lowest_rate_title),
                style = MaterialTheme.typography.bodyMedium,
                color = YellowRateText,
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = dailyAverageMeasurements.lowestHeartRate.toString(),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = BlackMain,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.bpm_title),
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreySubText,
                    textAlign = TextAlign.Center
                )
            }
        }
        Column(
            modifier = Modifier
                .background(color = GreenRateBg, shape = RoundedCornerShape(10.dp))
                .padding(vertical = 8.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.normal_rate_title),
                style = MaterialTheme.typography.bodyMedium,
                color = GreenRateText,
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = dailyAverageMeasurements.normalHeartRate.toString(),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = BlackMain,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.bpm_title),
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreySubText,
                    textAlign = TextAlign.Center
                )
            }
        }
        Column(
            modifier = Modifier
                .background(color = RedRateBg, shape = RoundedCornerShape(10.dp))
                .padding(vertical = 8.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.highest_rate_title),
                style = MaterialTheme.typography.bodyMedium,
                color = RedMain,
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = dailyAverageMeasurements.highestHeartRate.toString(),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = BlackMain,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.bpm_title),
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreySubText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}