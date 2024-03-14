package test.createx.heartrateapp.presentation.onboarding_data

import androidx.annotation.StringRes
import test.createx.heartrateapp.R

data class DataPage(
    @StringRes val titleResId: Int,
    @StringRes val subtitleResId: Int,
    @StringRes val descriptionResId: Int
) {
    companion object {
        fun get() = listOf(
            DataPage(
                R.string.welcome_title,
                R.string.welcome_subtitle,
                R.string.welcome_description
            ),
            DataPage(
                R.string.hey_title,
                R.string.hey_subtitle,
                R.string.hey_description
            ),
            DataPage(
                R.string.thanks_title,
                R.string.thanks_subtitle,
                R.string.thanks_description
            ),
            DataPage(
                R.string.good_title,
                R.string.good_subtitle,
                R.string.good_description
            ),
            DataPage(
                R.string.last_title,
                R.string.last_subtitle,
                R.string.last_description
            )
        )
    }
}
