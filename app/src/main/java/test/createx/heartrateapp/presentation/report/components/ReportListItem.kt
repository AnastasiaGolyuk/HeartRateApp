package test.createx.heartrateapp.presentation.report.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import test.createx.heartrateapp.R
import test.createx.heartrateapp.data.database.entity.HeartRate
import test.createx.heartrateapp.presentation.heart_rate_measurement.UserState
import test.createx.heartrateapp.presentation.heart_rate_report.HeartRateStatus
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.White
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReportListItem(heartRate: HeartRate) {

    var iconStateRes by remember {
        mutableIntStateOf(0)
    }

    for (userState in UserState.get()) {
        if (stringResource(id = userState.title) == heartRate.userState) {
            iconStateRes = userState.image
        }
    }

    var heartRateStatusInstance by remember {
        mutableStateOf(HeartRateStatus.get()[0])
    }

    for (heartRateStatus in HeartRateStatus.get()) {
        if (stringResource(id = heartRateStatus.title).substringBefore(' ') == heartRate.heartRateStatus) {
            heartRateStatusInstance = heartRateStatus
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.heart_icon),
                contentDescription = stringResource(id = R.string.heart_icon_description),
                modifier = Modifier.width(28.dp),
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier.width(IntrinsicSize.Min),
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = heartRate.heartRateValue.toString(),
                        color = BlackMain,
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.bpm_title),
                        color = GreySubText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (heartRate.userState.isNotNull()) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = GreyBg,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(vertical = 4.dp)
                            .width(56.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = iconStateRes),
                            contentDescription = stringResource(id = R.string.user_state_icon_description),
                            modifier = Modifier.size(24.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)
                .height(64.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        )
        {
            Text(
                text = heartRate.dateTime.format(DateTimeFormatter.ofPattern("MMM d , HH:mm")),
                color = GreySubText,
                style = MaterialTheme.typography.bodyMedium
            )
            Box(
                modifier = Modifier.background(
                    heartRateStatusInstance.colorBg,
                    RoundedCornerShape(10.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = heartRate.heartRateStatus,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = heartRateStatusInstance.colorText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun ItemPrev() {
    HeartRateAppTheme {
        ReportListItem(
            heartRate = HeartRate(
                1,
                1,
                80,
                "Exercise",
                "Normal",
                OffsetDateTime.now()
            )
        )
    }
}