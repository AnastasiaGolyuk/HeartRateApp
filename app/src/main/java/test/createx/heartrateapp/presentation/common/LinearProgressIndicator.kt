package test.createx.heartrateapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun LinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color,
    lineColor: Color,
    clipShape: Shape
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .background(lineColor, shape = clipShape)
                .fillMaxHeight()
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier.clip(clipShape)
                .background(progressColor)
                .fillMaxHeight()
                .fillMaxWidth(progress)
        )
    }
}