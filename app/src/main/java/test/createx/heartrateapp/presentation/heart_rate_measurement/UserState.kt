package test.createx.heartrateapp.presentation.heart_rate_measurement

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import test.createx.heartrateapp.R

data class UserState(
    @StringRes val title: Int,
    @DrawableRes val image: Int,
) {

    companion object {
        fun get() = listOf(
            UserState(
                R.string.normal_state_title,
                R.drawable.normal_emoji
            ), UserState(
                R.string.resting_state_title,
                R.drawable.resting_emoji
            ), UserState(
                R.string.walking_state_title,
                R.drawable.walking_emoji
            ), UserState(
                R.string.exercise_state_title,
                R.drawable.exercise_emoji
            )
        )
    }
}
