package test.createx.heartrateapp.presentation.paywall

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import test.createx.heartrateapp.R

data class SubscriptionBenefitItem(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val icon: Int,
) {
    companion object {
        fun get() = listOf(
            SubscriptionBenefitItem(
                R.string.heart_statistics_title,
                R.string.heart_statistics_description,
                R.drawable.chart_icon,
            ),
            SubscriptionBenefitItem(
                R.string.health_suggestions_title,
                R.string.health_suggestions_description,
                R.drawable.idea_icon,
            ),
            SubscriptionBenefitItem(
                R.string.heart_rate_history_title,
                R.string.heart_rate_history_description,
                R.drawable.report_icon,
            ),
        )
    }
}
