package test.createx.heartrateapp.presentation.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.EmptyDataScreen
import test.createx.heartrateapp.presentation.common.PeriodSelector
import test.createx.heartrateapp.presentation.statistics.components.averageRateChart.AverageChartComponent
import test.createx.heartrateapp.presentation.statistics.components.popularStateChart.PopularChartComponent
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.RedAction
import java.time.LocalDate

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel, navController: NavController) {

    val periods = stringArrayResource(id = R.array.periods_array).asList()

    val weekPeriodLabels = stringArrayResource(id = R.array.week_days_array).asList()

    val daysInCurrentMonth = LocalDate.now().lengthOfMonth()

    val monthPeriodLabels = (1..daysInCurrentMonth).map { it.toString() }

    LaunchedEffect(viewModel.isLoading.value) {
        if (!viewModel.isLoading.value) {
            if (viewModel.heartRatesWeeklyList.isEmpty()) {
                viewModel.togglePeriod(periods[1])
            } else {
                viewModel.togglePeriod(periods[0])
            }
        }
    }

    LaunchedEffect(viewModel.selectedPeriod.value) {
        if (viewModel.periodLabels.isEmpty() || viewModel.selectedPeriod.value == periods[0]) {
            viewModel.updateData(weekPeriodLabels)
        } else {
            viewModel.updateData(monthPeriodLabels)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBg)
    ) {
        if (viewModel.isLoading.value){
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp),
                    color = RedAction
                )
            }
        } else {
            if (viewModel.heartRatesAverageList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    EmptyDataScreen(
                        imageRes = R.drawable.stat_img,
                        titleRes = R.string.empty_statistics_title,
                        navController = navController
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    PeriodSelector(
                        periods = periods,
                        selectedPeriod = viewModel.selectedPeriod.value,
                        onPeriodChange = { period ->
                            if (period == periods[0] && viewModel.heartRatesWeeklyList.isEmpty()) {
                                /* No actions needed */
                            } else {
                                viewModel.togglePeriod(period)
                            }
                        }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
                    ) {
                        AverageChartComponent(
                            averageHeartRate = viewModel.averageRatePerPeriod.value,
                            heartRatesAverageList = viewModel.heartRatesAverageList,
                            periodAxisLabels = viewModel.periodLabels,
                            selectedValue = viewModel.selectedValue.value,
                            onValueChange = { selectedValue ->
                                viewModel.updateSelectedValue(
                                    selectedValue
                                )
                            }
                        )
                        if (viewModel.statesPopularList.isNotEmpty()) {
                            PopularChartComponent(chartDataList = viewModel.statesPopularList)
                        }
                    }
                }
            }
        }
    }
}

