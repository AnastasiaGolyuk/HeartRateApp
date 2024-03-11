package test.createx.heartrateapp.presentation.statistics.components

import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
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
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.lineSeries
import test.createx.heartrateapp.R
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreenRateBg
import test.createx.heartrateapp.ui.theme.GreenRateText
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.HeartRateAppTheme
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.RedRateBg
import test.createx.heartrateapp.ui.theme.White
import test.createx.heartrateapp.ui.theme.YellowRateBg
import test.createx.heartrateapp.ui.theme.YellowRateText

@Composable
fun AverageChartComponent(heartRatesAverageList:List<DailyAverageMeasurements>,periodAxisLabels:List<String>) {

    val bottomAxisValueFormatter =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _, _ -> periodAxisLabels[x.toInt()] }

    val markerList = heartRatesAverageList.map { it.dayOfPeriod }

    val eventMarker = rememberMarker(MarkerComponent.LabelPosition.AroundPoint)

    val selectedMarker = rememberSelectedMarker(MarkerComponent.LabelPosition.AroundPoint)

    var selectedValue by remember {
        mutableIntStateOf(markerList.last())
    }

    var selectedIndex by remember {
        mutableIntStateOf(heartRatesAverageList.size-1)
    }

    fun convertToPair(intValue: Int): Pair<Float, Marker> {
        val floatValue = intValue.toFloat()
        return if (intValue == selectedValue) {
            Pair(floatValue, selectedMarker)
        } else {
            Pair(floatValue, eventMarker)
        }
    }

    val modelProducer = remember { CartesianChartModelProducer.build() }

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

    LaunchedEffect(Unit) {
        modelProducer.tryRunTransaction {
            lineSeries {
                series(x = heartRatesAverageList.map { it.dayOfPeriod }, y = heartRatesAverageList.map { it.normalHeartRate })
            }
        }
    }

    LaunchedEffect(selectedValue){
        selectedIndex = heartRatesAverageList.indexOfFirst { it.dayOfPeriod==selectedValue }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Average measurement",
                style = MaterialTheme.typography.titleSmall,
                color = BlackMain,
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.heart_icon),
                    contentDescription = "",
                    modifier = Modifier.width(28.dp),
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "82",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = BlackMain,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "bpm",
                        modifier = Modifier.padding(bottom = 2.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = GreySubText,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        CartesianChartHost(
            rememberCartesianChart(
                rememberLineCartesianLayer(
                    axisValueOverrider = AxisValueOverrider.fixed(minX = 0f, maxX = (periodAxisLabels.size-1).toFloat(), minY = 20f),
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
                persistentMarkers = markerList.associate { convertToPair(it) },
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
            modelProducer,
            horizontalLayout = HorizontalLayout.fullWidth(
                scalableStartPadding = 15.dp,
                scalableEndPadding = 12.dp
            ),
            marker = rememberSelectedMarker(MarkerComponent.LabelPosition.AroundPoint),
            markerVisibilityChangeListener = object : MarkerVisibilityChangeListener {

                override fun onMarkerHidden(marker: Marker) {

                }

                override fun onMarkerShown(
                    marker: Marker,
                    markerEntryModels: List<Marker.EntryModel>
                ) {
                    selectedValue = markerEntryModels.last().entry.x.toInt()
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .background(color = YellowRateBg, shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 8.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Lowest rate",
                    style = MaterialTheme.typography.bodyMedium,
                    color = YellowRateText,
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = heartRatesAverageList[selectedIndex].lowestHeartRate.toString(),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = BlackMain,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "bpm",
                        modifier = Modifier.padding(bottom = 2.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = GreySubText,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                modifier = Modifier
                    .background(color = GreenRateBg, shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 8.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Normal rate",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreenRateText,
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = heartRatesAverageList[selectedIndex].normalHeartRate.toString(),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = BlackMain,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "bpm",
                        modifier = Modifier.padding(bottom = 2.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = GreySubText,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                modifier = Modifier
                    .background(color = RedRateBg, shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 8.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Highest rate",
                    style = MaterialTheme.typography.bodyMedium,
                    color = RedMain,
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = heartRatesAverageList[selectedIndex].highestHeartRate.toString(),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = BlackMain,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "bpm",
                        modifier = Modifier.padding(bottom = 2.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = GreySubText,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


//@Preview
//@Composable
//fun AvPrev() {
//    HeartRateAppTheme {
//        AverageChartComponent()
//    }
//}
