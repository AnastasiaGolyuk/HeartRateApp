package test.createx.heartrateapp.presentation.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.EmptyDataScreen
import test.createx.heartrateapp.presentation.statistics.components.AverageChartComponent
import test.createx.heartrateapp.presentation.statistics.components.DonutChartData
import test.createx.heartrateapp.presentation.statistics.components.PopularChartComponent
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.BlueState
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.LightBlueState
import test.createx.heartrateapp.ui.theme.OrangeState
import test.createx.heartrateapp.ui.theme.PurpleState
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.White

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel, navController: NavController) {
    val periods = viewModel.periods
    val selectedPeriod = viewModel.selectedPeriod

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBg)
            .verticalScroll(scrollState)
    ) {
        if (viewModel.heartRatesAverageList.isEmpty() ) {
            Box(modifier = Modifier.fillMaxSize()) {
                EmptyDataScreen(
                    imageRes = R.drawable.stat_img,
                    titleRes = R.string.empty_statistics_title,
                    navController = navController
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(White),
                horizontalArrangement = Arrangement.spacedBy(1.dp, Alignment.CenterHorizontally),
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
                                color = if (selectedPeriod.value == period) RedBg else White,
                                shape = RoundedCornerShape(9.dp)
                            )
                            .clip(shape = RoundedCornerShape(9.dp))
                            .clickable(onClick = { viewModel.togglePeriod(period) }),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = period,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (selectedPeriod.value == period) RedAction else BlackMain,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
            ) {
                AverageChartComponent(
                    heartRatesAverageList = viewModel.heartRatesAverageList,
                    periodAxisLabels = viewModel.periodLabels.value
                )
                PopularChartComponent(chartDataList = viewModel.statesPopularList)
            }
        }
    }
}

