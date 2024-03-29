package test.createx.heartrateapp.presentation.onboarding_data.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.Picker
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.White

@Composable
fun ExpandablePickerButton(
    title: String,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit,
    items: List<String>,
    visibleItemsCount: Int
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(if (!isVisible) 56.dp else 190.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = White
        ),
        contentPadding = PaddingValues(16.dp),
        onClick = onToggleVisibility
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    style = MaterialTheme.typography.titleSmall,
                    color = BlackMain,
                    text = title,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Icon(
                    painter = painterResource(id = R.drawable.drop_down_icon),
                    contentDescription = "Drop down",
                    tint = RedAction
                )
            }
            if (isVisible) {
                Picker(
                    values = items,
                    visibleItemsCount = visibleItemsCount,
                )
            }
        }
    }
}

