package test.createx.heartrateapp.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    positionValue: Float,
    primaryColor: Color,
    secondaryColor: Color,
    maxValue: Int = 100,
) {

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val radius = width / 2f - 25f
            val circleThickness = width / 25f


            drawCircle(
                style = Stroke(
                    width = circleThickness
                ),
                color = secondaryColor,
                radius = radius,
            )

            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = (360f / maxValue) * positionValue,
                style = Stroke(
                    width = circleThickness + 10f,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = radius * 2f,
                    height = radius * 2f
                ),
                topLeft = Offset(
                    (width - radius * 2f) / 2f,
                    (width - radius * 2f) / 2f
                )
            )
        }
    }
}
