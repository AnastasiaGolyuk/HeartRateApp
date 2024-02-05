package test.createx.heartrateapp.domain.model

import androidx.annotation.DrawableRes
import test.createx.heartrateapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
    @DrawableRes val bg: Int
){
    companion object {
        fun get() = listOf(
            Page(
                "Heart rate measurement",
                "Easy way to monitor your health-measure heart rate anytime and anywhere",
                R.drawable.onboarding_first,
                R.drawable.onboarding_bg_first
            ),
            Page(
                "Measurements and mood statistics",
                "Get graphs on the dynamics of your health. Now you won’t miss anything important!",
                R.drawable.onboarding_second,
                R.drawable.onboarding_bg_second
            )
        )
    }
}
