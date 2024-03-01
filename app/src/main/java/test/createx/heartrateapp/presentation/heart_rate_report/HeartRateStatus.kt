package test.createx.heartrateapp.presentation.heart_rate_report

import androidx.compose.ui.graphics.Color
import test.createx.heartrateapp.ui.theme.GreenRateBg
import test.createx.heartrateapp.ui.theme.GreenRateText
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.RedRateBg
import test.createx.heartrateapp.ui.theme.YellowRateBg
import test.createx.heartrateapp.ui.theme.YellowRateText

data class HeartRateStatus(val title: String,
                           val colorBg: Color,
                           val colorText: Color) {
    companion object{
        fun get() = listOf(HeartRateStatus("Low rate", YellowRateBg, YellowRateText),
            HeartRateStatus("Normal rate", GreenRateBg, GreenRateText),
            HeartRateStatus("High rate", RedRateBg, RedMain),)
    }
}