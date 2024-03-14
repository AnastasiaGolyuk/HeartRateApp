package test.createx.heartrateapp.presentation.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.navigation.Graph
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.White

@Composable
fun WorkoutScreen(navController:NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBg)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(color = White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.fillMaxHeight(0.26f),
                    painter = painterResource(id = R.drawable.workout_img),
                    contentDescription = stringResource(R.string.workout_intro_image_description),
                    contentScale = ContentScale.FillHeight
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.workout_intro_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = BlackMain,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.workout_intro_description),
                        style = MaterialTheme.typography.bodyMedium,
                        color = GreySubText,
                        textAlign = TextAlign.Center
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.width(159.dp),
                    thickness = 1.dp,
                    color = RedMain.copy(alpha = 0.25f)
                )
                Text(
                    text = stringResource(R.string.workout_intro_subscription_details_text),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = RedMain,
                    textAlign = TextAlign.Center
                )
            }
        }
        Column(modifier = Modifier
            .align(Alignment.BottomCenter)) {

            ElevatedButton(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.WorkoutGraph.route)
                },
                modifier = Modifier
                    .height(48.dp)
                    .shadow(
                        elevation = 10.dp,
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
                    text = stringResource(R.string.workout_intro_button_text),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxHeight(0.065f)
            )
        }
    }
}