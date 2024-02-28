package test.createx.heartrateapp.presentation.paywall

import androidx.annotation.DrawableRes
import test.createx.heartrateapp.R

data class SubscriptionBenefitItem(
    val title: String,
    val description: String,
    @DrawableRes val icon: Int,
) {
    companion object {
        fun get() = listOf(
            SubscriptionBenefitItem(
                "Heart statistics",
                "A simple way to monitor your health is to measure your heart rate anytime, anywhere.",
                R.drawable.chart_icon,
            ),
            SubscriptionBenefitItem(
                "Health suggestions",
                "Get useful tips for your health. Now you won't miss anything important.",
                R.drawable.idea_icon,
            ),
            SubscriptionBenefitItem(
                "Heart rate history",
                "Get health trend graphs and view health reports.",
                R.drawable.report_icon,
            ),
        )
    }
}
