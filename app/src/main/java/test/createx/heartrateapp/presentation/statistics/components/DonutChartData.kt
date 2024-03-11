package test.createx.heartrateapp.presentation.statistics.components

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color


data class DonutChartData(val color: Color, val data: Float, val state: String, @DrawableRes val  iconRes: Int)
