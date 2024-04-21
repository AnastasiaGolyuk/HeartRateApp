package test.createx.heartrateapp.presentation.workout_exercises

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import test.createx.heartrateapp.R

data class Workout(
    @StringRes val title: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val descriptionRes: Int
) {
    companion object {
        fun get() = listOf(
            Workout(
                R.string.squats_workout_title,
                R.drawable.squat_img,
                R.string.squats_workout_description
            ),
            Workout(
                R.string.plank_workout_title,
                R.drawable.plank_img,
                R.string.plank_workout_description
            ),
            Workout(
                R.string.rope_workout_title,
                R.drawable.rope_img,
                R.string.rope_workout_description
            ),
            Workout(
                R.string.step_workout_title,
                R.drawable.step_img,
                R.string.step_workout_description
            ),
            Workout(
                R.string.biceps_workout_title,
                R.drawable.biceps_img,
                R.string.biceps_workout_description
            ),
        )
    }
}
