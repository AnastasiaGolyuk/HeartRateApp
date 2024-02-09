package test.createx.heartrateapp.presentation.onboarding_data.components.dropdown_component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.ui.theme.BlackMain

@Composable
fun RowScope.ButtonContent(title:String, @DrawableRes iconDrawableRes: Int = -1) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)) {
        if (iconDrawableRes!=-1){
            Icon(painter = painterResource(id = iconDrawableRes), contentDescription = "", tint = BlackMain)
        }
        Text(
            style = MaterialTheme.typography.titleSmall,
            color = BlackMain,
            text = title,
            textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}