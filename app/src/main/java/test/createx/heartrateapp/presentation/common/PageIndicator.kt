package test.createx.heartrateapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.ui.theme.RedMain

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = RedMain,
    unselectedColor: Color = RedMain.copy(alpha = 0.2f)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        repeat(pageSize) { page ->
            Box(
                modifier = Modifier
                    .width(16.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
            )
        }
    }
}