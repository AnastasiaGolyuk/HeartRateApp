package test.createx.heartrateapp.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.EmptyDataScreen
import test.createx.heartrateapp.presentation.report.components.ReportListItem
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.RedBg
import test.createx.heartrateapp.ui.theme.White
import java.time.format.DateTimeFormatter

@Composable
fun ReportScreen(
    navController: NavController, viewModel: ReportViewModel
) {
    val selectedPeriod = viewModel.selectedPeriod

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBg)
    ) {
        if (viewModel.heartRatesDailyList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                EmptyDataScreen(
                    imageRes = R.drawable.report_img2,
                    titleRes = R.string.empty_reports_title,
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
                viewModel.periods.forEach { period ->
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
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                viewModel.heartRatesDailyList.forEach { dailyRecords ->
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = dailyRecords.dateTime.format(DateTimeFormatter.ofPattern("MMMM d, y")),
                        style = MaterialTheme.typography.displaySmall,
                        color = BlackMain
                    )
                    LazyColumn {
                        items(items = dailyRecords.heartRateList) { heartRate ->
                            Spacer(modifier = Modifier.height(16.dp))
                            ReportListItem(heartRate = heartRate)
                        }
                    }
                }
            }

        }
    }
}



