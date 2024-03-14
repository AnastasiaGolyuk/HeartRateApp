package test.createx.heartrateapp.presentation.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.presentation.common.AnimationLottie
import test.createx.heartrateapp.presentation.onboarding.Page
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.RedMain

@Composable
fun OnboardingPage(
    page: Page,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = BlackMain,
                        )
                    )
                    {
                        append(stringResource(id = page.title).substringBeforeLast(' '))
                    }
                    withStyle(style = SpanStyle(color = RedMain, fontWeight = FontWeight.Bold)) {
                        append(" ${stringResource(id = page.title).substringAfterLast(' ')}")
                    }
                },
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = page.description),
                style = MaterialTheme.typography.bodyLarge,
                color = GreySubText
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(370.dp)
        ) {
            AnimationLottie(
                animationId = page.image,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPagePreview() {
    HeartRateAppTheme {
        OnboardingPage(page = Page.get()[0])
    }
}