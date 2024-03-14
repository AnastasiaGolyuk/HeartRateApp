package test.createx.heartrateapp.presentation.onboarding

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import test.createx.heartrateapp.R

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @RawRes val image: Int,
    @RawRes val bg: Int
){
    companion object {
        fun get() = listOf(
            Page(
                R.string.heart_rate_measurement_title,
                R.string.heart_rate_measurement_description,
                R.raw.image1,
                R.raw.onboarding1
            ),
            Page(
                R.string.measurements_and_mood_statistics_title,
                R.string.measurements_and_mood_statistics_description,
                R.raw.image2,
                R.raw.onboarding2
            )
        )
    }
}
