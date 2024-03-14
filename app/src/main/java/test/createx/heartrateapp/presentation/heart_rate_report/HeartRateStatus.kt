package test.createx.heartrateapp.presentation.heart_rate_report

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import test.createx.heartrateapp.R
import test.createx.heartrateapp.ui.theme.GreenRateBg
import test.createx.heartrateapp.ui.theme.GreenRateText
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.RedRateBg
import test.createx.heartrateapp.ui.theme.YellowRateBg
import test.createx.heartrateapp.ui.theme.YellowRateText

data class HeartRateStatus(
    @StringRes val title: Int,
    val colorBg: Color,
    val colorText: Color
) {
    companion object {
        fun get() = listOf(
            HeartRateStatus(
                R.string.low_rate_title,
                YellowRateBg,
                YellowRateText
            ),
            HeartRateStatus(R.string.normal_rate_title, GreenRateBg, GreenRateText),
            HeartRateStatus(R.string.high_rate_title, RedRateBg, RedMain),
        )
    }
}