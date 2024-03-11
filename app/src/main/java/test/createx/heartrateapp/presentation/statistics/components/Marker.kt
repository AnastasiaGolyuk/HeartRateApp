package test.createx.heartrateapp.presentation.statistics.components

import android.graphics.Typeface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.sourceInformationMarkerStart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.component.rememberLayeredComponent
import com.patrykandpatrick.vico.compose.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.marker.Marker
import test.createx.heartrateapp.ui.theme.GreenRateText
import test.createx.heartrateapp.ui.theme.White
import test.createx.heartrateapp.ui.theme.YellowRateText

@Composable
internal fun rememberMarker(
    labelPosition: MarkerComponent.LabelPosition = MarkerComponent.LabelPosition.Top,
): Marker {

    val label =
        rememberTextComponent(
            color = Color.Transparent,
            textSize = 0.sp
        )
    val indicatorFrontComponent =
        rememberShapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.surface)
    val indicatorCenterComponent = rememberShapeComponent(Shapes.pillShape)
    val indicatorRearComponent = rememberShapeComponent(Shapes.pillShape)
    val indicator =
        rememberLayeredComponent(
            rear = indicatorRearComponent,
            front =
            rememberLayeredComponent(
                rear = indicatorCenterComponent,
                front = indicatorFrontComponent,
                padding = dimensionsOf(12.dp),
            ),
            padding = dimensionsOf(2.dp),
        )
    val guideline =
        null
    return remember(label, labelPosition, indicator, guideline) {
        object : MarkerComponent(label, labelPosition, indicator, guideline) {
            init {
                indicatorSizeDp = 12f
                indicatorRearComponent.color = GreenRateText.toArgb()
                indicatorCenterComponent.color = White.toArgb()
            }

        }
    }
}

@Composable
internal fun rememberSelectedMarker(
    labelPosition: MarkerComponent.LabelPosition = MarkerComponent.LabelPosition.Top,
): Marker {

    val label =
        rememberTextComponent(
            color = Color.Transparent,
            textSize = 0.sp
        )
    val indicatorFrontComponent =
        rememberShapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.surface)
    val indicatorCenterComponent = rememberShapeComponent(Shapes.pillShape)
    val indicatorRearComponent = rememberShapeComponent(Shapes.pillShape)
    val indicator =
        rememberLayeredComponent(
            rear = indicatorRearComponent,
            front =
            rememberLayeredComponent(
                rear = indicatorCenterComponent,
                front = indicatorFrontComponent,
                padding = dimensionsOf(3.dp),
            ),
            padding = dimensionsOf(2.dp),
        )
    val guideline =
        null
    return remember(label, labelPosition, indicator, guideline) {
        object : MarkerComponent(label, labelPosition, indicator, guideline) {


            init {
                indicatorSizeDp = 24f
                indicatorRearComponent.color = YellowRateText.toArgb()
                indicatorCenterComponent.color = White.toArgb()
//                indicatorCenterComponent.
                indicatorFrontComponent.color = YellowRateText.toArgb()

//                onApplyEntryColor = { entryColor ->
//                    indicatorFrontComponent.color =
//                        entryColor
//                    with(indicatorCenterComponent) {
//                        color = entryColor
//                        setShadow(
//                            radius = INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS,
//                            color = entryColor
//                        )
//                    }
//                }
//                indicatorFrontComponent.color = YellowRateText.toArgb()


            }

        }
    }
}


private const val INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS = 12f
