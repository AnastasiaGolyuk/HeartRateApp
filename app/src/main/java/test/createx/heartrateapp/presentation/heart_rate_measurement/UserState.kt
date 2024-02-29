package test.createx.heartrateapp.presentation.heart_rate_measurement

import androidx.annotation.DrawableRes
import test.createx.heartrateapp.R

data class UserState(
    val title: String,
    @DrawableRes val image: Int,
) {

    companion object {
        fun get() = listOf(
            UserState(
                "Normal",
                R.drawable.normal_emoji
            ), UserState(
                "Resting",
                R.drawable.resting_emoji
            ), UserState(
                "Walking",
                R.drawable.walking_emoji
            ), UserState(
                "Exercise",
                R.drawable.exercise_emoji
            )
        )
    }
}
