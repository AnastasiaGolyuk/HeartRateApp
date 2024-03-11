package test.createx.heartrateapp.presentation.statistics.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.heart_rate_measurement.UserState
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.White

@Composable
fun PopularChartComponent(chartDataList: List<DonutChartData>) {
    val mostlyState = chartDataList.maxOfOrNull { it.data }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {

        Text(
            text = "Your popular state",
            style = MaterialTheme.typography.titleSmall,
            color = BlackMain,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .padding(8.dp), contentAlignment = Alignment.Center) {
                DonutChart(
                    pieDataPoints = chartDataList,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Mostly",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GreySubText,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${(mostlyState!!*100).toInt()}%",
                        modifier = Modifier.padding(bottom = 2.dp),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = BlackMain,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(color = GreyBg, shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                chartDataList.forEach { data ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 18.dp, height = 6.dp)
                                .background(
                                    color = data.color,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        )
                        Image(
                            painter = painterResource(id = data.iconRes),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = data.state,
                            modifier = Modifier.padding(bottom = 2.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = BlackMain,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
    }
}
