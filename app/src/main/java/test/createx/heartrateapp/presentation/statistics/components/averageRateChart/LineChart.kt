package test.createx.heartrateapp.presentation.statistics.components.averageRateChart

import android.graphics.Typeface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.values.AxisValueOverrider
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerVisibilityChangeListener
import com.patrykandpatrick.vico.core.model.CartesianChartModel
import com.patrykandpatrick.vico.core.model.LineCartesianLayerModel
import com.patrykandpatrick.vico.core.scroll.Scroll
import test.createx.heartrateapp.presentation.statistics.DailyAverageMeasurements
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedRateBg

@Composable
fun LineChart(
    periodAxisLabels: List<String>,
    heartRatesAverageList: List<DailyAverageMeasurements>,
    markerList: List<Int>,
    selectedValue: Int,
    onMarkerShow: (Int) -> Unit
) {

    val bottomAxisValueFormatter =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _, _ -> periodAxisLabels[x.toInt()] }

    val defaultMarker = rememberMarker(MarkerComponent.LabelPosition.AroundPoint)

    val selectedMarker = rememberSelectedMarker(MarkerComponent.LabelPosition.AroundPoint)

    val style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
    val resolver = LocalFontFamilyResolver.current

    val typeface = remember(resolver, style) {
        resolver.resolve(
            fontFamily = style.fontFamily,
            fontWeight = style.fontWeight ?: FontWeight.Normal,
            fontStyle = style.fontStyle ?: FontStyle.Normal,
            fontSynthesis = style.fontSynthesis ?: FontSynthesis.All,
        )
    }.value as Typeface

    val model =
        CartesianChartModel(
            LineCartesianLayerModel.build {
                series(
                    x = heartRatesAverageList.map { it.dayOfPeriod },
                    y = heartRatesAverageList.map { it.normalHeartRate })
            }
        )

    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(
                axisValueOverrider = AxisValueOverrider.fixed(
                    minX = 0f,
                    maxX = (periodAxisLabels.size - 1).toFloat(),
                    minY = 20f
                ),
                lines =
                listOf(
                    rememberLineSpec(
                        thickness = 3.dp,
                        shader = DynamicShaders.color(RedRateBg),
                        backgroundShader = null,
                        pointConnector = DefaultPointConnector(cubicStrength = 0f),
                    ),
                ),
            ),
            persistentMarkers = markerList.associate {
                convertToPair(
                    it,
                    selectedValue,
                    selectedMarker,
                    defaultMarker
                )
            },
            startAxis = rememberStartAxis(
                label = rememberTextComponent(
                    typeface = typeface,
                    color = GreySubText,
                    padding = dimensionsOf(horizontal = 8.dp, vertical = 2.dp),
                ),
                axis = null,
                tick = null,
                guideline = LineComponent(color = RedRateBg.toArgb(), thicknessDp = 1.dp.value)
            ),
            bottomAxis = rememberBottomAxis(
                label = rememberTextComponent(
                    typeface = typeface,
                    color = GreySubText,
                    padding = dimensionsOf(horizontal = 5.dp, vertical = 2.dp),
                ),
                valueFormatter = bottomAxisValueFormatter,
                axis = null,
                tick = null,
                guideline = LineComponent(color = RedRateBg.toArgb(), thicknessDp = 1.dp.value)
            ),
        ),
        model = model,
        scrollState = rememberVicoScrollState(initialScroll = Scroll.Absolute.x(maxOf(selectedValue.toFloat()-5.5f,-0.5f))),
        horizontalLayout = HorizontalLayout.fullWidth(
            scalableStartPadding = 15.dp,
            scalableEndPadding = 12.dp
        ),
        marker = rememberSelectedMarker(MarkerComponent.LabelPosition.AroundPoint),
        markerVisibilityChangeListener = object : MarkerVisibilityChangeListener {

            override fun onMarkerHidden(marker: Marker) {
                /* No actions needed */
            }

            override fun onMarkerShown(
                marker: Marker,
                markerEntryModels: List<Marker.EntryModel>
            ) {
                onMarkerShow(markerEntryModels.last().entry.x.toInt())
            }
        }
    )
}

private fun convertToPair(
    intValue: Int,
    selectedValue: Int,
    selectedMarker: Marker,
    defaultMarker: Marker
): Pair<Float, Marker> {
    val floatValue = intValue.toFloat()
    return if (intValue == selectedValue) {
        Pair(floatValue, selectedMarker)
    } else {
        Pair(floatValue, defaultMarker)
    }
}