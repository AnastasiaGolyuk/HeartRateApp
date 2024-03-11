package test.createx.heartrateapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.ui.theme.GreyProgressbar
import test.createx.heartrateapp.ui.theme.RedProgressbar

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

@Preview
@Composable
fun PreviewLinear() {
    LinearProgressIndicator(
        modifier = Modifier.width(328.dp).height(16.dp),
        progress = 30f / 60,
        progressColor = RedProgressbar,
        lineColor = GreyProgressbar,
        clipShape = RoundedCornerShape(9.dp)
    )
}