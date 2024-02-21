package test.createx.heartrateapp.presentation.paywall

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.navigation.Route
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.RedMain

@Composable
fun PaywallScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            IconButton(onClick = { navController.navigate(Route.OnboardingDataScreen.route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.close_icon),
                    contentDescription = "Favorite",
                    tint = GreySubText
                )
            }
            TextButton(
                onClick = { /* Handle button click event */ },
                content = {
                    Text(
                        text = "Restore",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = GreySubText
                    )
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Use 100% All the functionality of the App",
                style = MaterialTheme.typography.displayMedium,
                color = BlackMain,
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.Top,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.chart_icon),
                        contentDescription = "",
                        tint = RedMain
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            2.dp, Alignment.CenterVertically
                        ),
                    ) {
                        Text(
                            text = "Heart statistics",
                            style = MaterialTheme.typography.titleSmall,
                            color = BlackMain,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "A simple way to monitor your health is to measure your heart rate anytime, anywhere.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = GreySubText
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.Top,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.idea_icon),
                        contentDescription = "",
                        tint = RedMain
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            2.dp, Alignment.CenterVertically
                        ),
                    ) {
                        Text(
                            text = "Health suggestions",
                            style = MaterialTheme.typography.titleSmall,
                            color = BlackMain,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "Get useful tips for your health. Now you won't miss anything important.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = GreySubText
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.Top,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.report_icon ),
                        contentDescription = "",
                        tint = RedMain
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            2.dp, Alignment.CenterVertically
                        ),
                    ) {
                        Text(
                            text = "Heart rate history",
                            style = MaterialTheme.typography.titleSmall,
                            color = BlackMain,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "Get health trend graphs and view health reports.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = GreySubText
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(156.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.paywall_bg),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.padding(top = 41.dp), text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = RedMain,
                                )
                            ) {
                                append("Free 3 days trial, ")
                            }
                            withStyle(style = SpanStyle(color = GreySubText)) {
                                append("then \$7.99/Week")
                            }
                        }, style = MaterialTheme.typography.bodyMedium
                    )
                    ElevatedButton(
                        onClick = {
                            navController.navigate(Route.OnboardingDataScreen.route)
                        },
                        modifier = Modifier
                            .size(width = 328.dp, height = 48.dp)
                            .shadow(
                                elevation = 18.dp,
                                shape = RoundedCornerShape(50.dp),
                                clip = true,
                                ambientColor = Color(0xFFCC0909),
                                spotColor = Color(0xFFCC0909),
                            ),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = RedMain,
                            disabledContainerColor = RedMain.copy(alpha = 0.5f),
                            disabledContentColor = RedMain.copy(alpha = 0.5f),
                        )
                    ) {
                        Text(
                            text = "Get started",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.White
                        )
                    }
                    Row(
                        modifier = Modifier.height(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextButton(
                            onClick = { /* Handle button click event */ },
                            content = {
                                Text(
                                    text = "Terms of Use",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = GreySubText
                                )
                            },
                            contentPadding = PaddingValues(0.dp)
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .height(16.dp)
                                .width(1.dp),
                            color = RedMain.copy(alpha = 0.2f)
                        )
                        TextButton(
                            onClick = { /* Handle button click event */ },
                            content = {
                                Text(
                                    text = "Privacy Policy",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = GreySubText
                                )
                            },
                            contentPadding = PaddingValues(0.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaywallPrev() {
    HeartRateAppTheme {
        PaywallScreen(navController = rememberNavController())
    }
}