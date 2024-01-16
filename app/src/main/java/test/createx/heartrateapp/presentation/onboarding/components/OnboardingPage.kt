package test.createx.heartrateapp.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.data.model.Page
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.RedMain

@Composable
fun OnboardingPage(
    page: Page,
) {

    Column {
        val pages = Page.get()
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = BlackMain,
                    )
                )
                {
                    append(page.title.substringBeforeLast(' '))
                }
                withStyle(style = SpanStyle(color = RedMain, fontWeight = FontWeight.Bold)) {
                    append(" ${page.title.substringAfterLast(' ')}")
                }
            },
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = GreySubText
        )
        Box {
            Image(
                painter = painterResource(id = page.bg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 145.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )
            Image(
                painter = painterResource(id = page.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = if (page == pages[0]) 45.dp else 10.dp),
                contentScale = ContentScale.Fit,
                alignment = Alignment.TopCenter
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