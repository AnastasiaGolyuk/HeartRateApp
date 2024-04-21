package test.createx.heartrateapp.presentation.workout_exercises.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.workout_exercises.Workout
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.RedBg

@Composable
fun WorkoutExercisePage(
    workout: Workout,
) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.72f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.weight(1f),
//                    .fillMaxWidth()
//                    .fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = workout.title),
                    style = MaterialTheme.typography.titleMedium
                )
                Image(
                    painter = painterResource(id = workout.imageRes),
                    contentDescription = stringResource(R.string.workout_image_description),
                    modifier = Modifier.fillMaxHeight(),
                    contentScale = ContentScale.FillHeight
                )
            }
            Column(
                modifier = Modifier

                    .offset(y = (-15).dp)
                    .background(color = RedBg, shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                stringResource(id = workout.descriptionRes).split('\n').forEach { descriptionItem ->
                    Text(
                        textAlign = TextAlign.Start,
                        text = descriptionItem,
                        style = MaterialTheme.typography.bodyMedium,
                        color = GreySubText
                    )
                }

            }
        }
}

@Preview(showBackground = true)
@Composable
fun ExercisePrev() {
    HeartRateAppTheme {
        WorkoutExercisePage(workout = Workout.get()[0])
    }
}