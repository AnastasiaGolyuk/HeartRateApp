package test.createx.heartrateapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.navigation.Route
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedMain

@Composable
fun BoxScope.EmptyDataScreen(
    imageRes: Int, titleRes: Int,
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        Box(contentAlignment = Alignment.TopCenter) {
            Image(
                modifier = Modifier.size(310.dp),
                painter = painterResource(id = imageRes),
                contentDescription = stringResource(R.string.empty_data_image_description),
            )
            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.bodyMedium,
                color = GreySubText,
                textAlign = TextAlign.Center
            )
        }
    }
    ElevatedButton(
        onClick = {
            navController.navigate(Route.HeartRateScreen.route)
        },
        modifier = Modifier
            .padding(bottom = 50.dp)
            .height(48.dp)
            .align(Alignment.BottomCenter)
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
            text = stringResource(R.string.empty_statistics_button_text),
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
    }
}