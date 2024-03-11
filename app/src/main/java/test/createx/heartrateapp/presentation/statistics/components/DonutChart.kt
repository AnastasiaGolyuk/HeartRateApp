package test.createx.heartrateapp.presentation.statistics.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

data class ArcData(
    val targetSweepAngle: Float,
    val startAngle: Float,
    val color: Color,
)

@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    pieDataPoints: List<DonutChartData>,
) {
    val gapDegrees = 14
    val numberOfGaps = pieDataPoints.size
    val remainingDegrees = 360f - (gapDegrees * numberOfGaps)
    val total = pieDataPoints.fold(0f) { acc, pieData -> acc + pieData.data }.div(remainingDegrees)
    var currentSum = 0f
    val arcs = pieDataPoints.mapIndexed { index, it ->
        val startAngle = currentSum + (index * gapDegrees)
        currentSum += it.data / total

        ArcData(
            targetSweepAngle = it.data / total,
            startAngle = -90 + startAngle,
            color = it.color
        )
    }

    Canvas(
        modifier = modifier
            .scale(1f)
    ) {
        val stroke = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)

        arcs.map {
            drawArc(
                startAngle = it.startAngle,
                sweepAngle = it.targetSweepAngle,
                color = it.color,
                useCenter = false,
                style = stroke,
            )
        }
    }
}
