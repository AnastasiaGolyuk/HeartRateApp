package test.createx.heartrateapp.presentation.paywall.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.paywall.SubscriptionBenefitItem
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedMain

@Composable
fun SubscriptionBenefitComponent(benefitItem: SubscriptionBenefitItem) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            16.dp, Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            painter = painterResource(id = benefitItem.icon),
            contentDescription = stringResource(R.string.benefit_icon_description),
            tint = RedMain
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(
                2.dp, Alignment.CenterVertically
            ),
        ) {
            Text(
                text = stringResource(benefitItem.title),
                style = MaterialTheme.typography.titleSmall,
                color = BlackMain,
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(benefitItem.description),
                style = MaterialTheme.typography.bodyMedium,
                color = GreySubText
            )
        }
    }
}