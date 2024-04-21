package test.createx.heartrateapp.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.navigation.Graph
import test.createx.heartrateapp.presentation.navigation.Route
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.White

@Composable
fun SettingsScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = White
                ),
                contentPadding = PaddingValues(16.dp),
                onClick = { navController.navigate(Graph.ProfileDetailsGraph.route)}) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(R.string.settings_profile_details_title),
                        color = BlackMain,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = White
                ),
                contentPadding = PaddingValues(16.dp),
                onClick = { /* TODO Handle button click event */ }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(R.string.settings_contact_us_title),
                        color = BlackMain,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    )
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = White
                ),
                shape = RectangleShape,
                contentPadding = PaddingValues(16.dp),
                onClick = { /* TODO Handle button click event */ }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(R.string.settings_review_title),
                        color = BlackMain,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    )
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = White
                ),
                shape = RectangleShape,
                contentPadding = PaddingValues(16.dp),
                onClick = { /* TODO Handle button click event */ }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(R.string.privacy_policy_title),
                        color = BlackMain,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    )
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = White
                ),
                contentPadding = PaddingValues(16.dp),
                onClick = { /* TODO Handle button click event */ }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(R.string.terms_of_use_title),
                        color = BlackMain,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    )
                }
            }
        }

    }
}