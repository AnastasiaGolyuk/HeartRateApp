package test.createx.heartrateapp.presentation.onboarding

import androidx.annotation.RawRes
import test.createx.heartrateapp.R

data class Page(
    val title: String,
    val description: String,
    @RawRes val image: Int,
    @RawRes val bg: Int
){
    companion object {
        fun get() = listOf(
            Page(
                "Heart rate measurement",
                "Easy way to monitor your health-measure heart rate anytime and anywhere",
                R.raw.image1,
                R.raw.onboarding1
            ),
            Page(
                "Measurements and mood statistics",
                "Get graphs on the dynamics of your health. Now you won’t miss anything important!",
                R.raw.image2,
                R.raw.onboarding2
            )
        )
    }
}
