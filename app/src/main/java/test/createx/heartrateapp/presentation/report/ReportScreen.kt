package test.createx.heartrateapp.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import test.createx.heartrateapp.presentation.report.components.ReportListItem
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.RedAction
import java.time.format.DateTimeFormatter

@Composable
fun ReportScreen(
    navController: NavController, viewModel: ReportViewModel
) {

    val periods = stringArrayResource(id = R.array.periods_array)

    LaunchedEffect(viewModel.isLoading.value) {
        if (!viewModel.isLoading.value) {
            viewModel.setPeriodsList(periods.asList())
        }
    }

    val selectedPeriod = viewModel.selectedPeriod

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBg)
    ) {
        if (viewModel.isLoading.value) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp),
                    color = RedAction
                )
            }
        } else {
            if (viewModel.heartRatesDailyList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    EmptyDataScreen(
                        imageRes = R.drawable.report_img2,
                        titleRes = R.string.empty_reports_title,
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
                        periods = viewModel.periods,
                        selectedPeriod = selectedPeriod.value,
                        onPeriodChange = { period ->
                            if (period == periods[0] && viewModel.heartRatesWeeklyList.isEmpty()) {
                                /* No actions needed */
                            } else {
                                viewModel.togglePeriod(period)
                            }
                        },
                    )
                    Column {
                        viewModel.heartRatesDailyList.forEach { dailyRecords ->
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                text = dailyRecords.dateTime.format(DateTimeFormatter.ofPattern("MMMM d, y")),
                                style = MaterialTheme.typography.displaySmall,
                                color = BlackMain
                            )
                            Column {
                                dailyRecords.heartRateList.forEach { heartRate ->
                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                    ) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        ReportListItem(heartRate = heartRate)
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}



