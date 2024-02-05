package test.createx.heartrateapp.presentation.onboarding_data.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.domain.model.DataPage
import test.createx.heartrateapp.presentation.onboarding_data.OnboardingDataViewModel
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.RedMain

@Composable
fun OnboardingDataPage(dataPage: DataPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = dataPage.title,
                style = MaterialTheme.typography.displayLarge,
                color = BlackMain
            )
            Text(
                text = dataPage.subtitle,
                style = MaterialTheme.typography.displayLarge,
                color = RedMain
            )
        }
        Text(
            text = dataPage.description,
            style = MaterialTheme.typography.bodySmall,
            color = GreySubText
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingDataPagePreview() {
    HeartRateAppTheme {
        OnboardingDataPage(dataPage = DataPage.get()[0])
    }
}