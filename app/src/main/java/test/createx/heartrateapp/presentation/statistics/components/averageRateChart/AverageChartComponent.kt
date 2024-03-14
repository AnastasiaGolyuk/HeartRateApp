package test.createx.heartrateapp.presentation.statistics.components.averageRateChart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.statistics.DailyAverageMeasurements
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.White

@Composable
fun AverageChartComponent(
    averageHeartRate: Int,
    heartRatesAverageList: List<DailyAverageMeasurements>,
    periodAxisLabels: List<String>,
    selectedValue: Int,
    onValueChange:(Int)->Unit
) {

    val markerList = heartRatesAverageList.map { it.dayOfPeriod }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.average_measurement_title),
                style = MaterialTheme.typography.titleSmall,
                color = BlackMain,
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.heart_icon),
                    contentDescription = stringResource(R.string.heart_icon_description),
                    modifier = Modifier.width(28.dp),
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = averageHeartRate.toString(),
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
        LineChart(
            periodAxisLabels = periodAxisLabels,
            heartRatesAverageList = heartRatesAverageList,
            markerList = markerList,
            selectedValue = selectedValue,
            onMarkerShow = { selectedMarker -> onValueChange(selectedMarker) }
        )
        PeakRateValues(dailyAverageMeasurements = heartRatesAverageList[heartRatesAverageList.indexOfFirst { it.dayOfPeriod == selectedValue }])
    }
}
