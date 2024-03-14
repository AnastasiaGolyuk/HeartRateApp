package test.createx.heartrateapp.presentation.profile.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.onboarding_data.components.TextInputComponent
import test.createx.heartrateapp.presentation.onboarding_data.components.dropdown_component.ButtonContent
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.White

@Composable
fun ExpandableTextInputButton(
    title: String,
    iconRes: Int = -1,
    shape: RoundedCornerShape,
    value: String,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit,
    onInput: (String) -> Unit
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .defaultMinSize(minHeight = 56.dp),
        shape = shape,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = White
        ),
        contentPadding = PaddingValues(16.dp),
        onClick = onToggleVisibility
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp,Alignment.Top)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonContent(title = title, iconDrawableRes = iconRes)
                if ((value.isNotEmpty()) && !isVisible) {
                    Text(
                        style = MaterialTheme.typography.titleSmall,
                        color = RedAction,
                        text = value,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.add_icon),
                        contentDescription = stringResource(R.string.add_icon_description),
                        tint = RedAction
                    )
                }
            }
            if (isVisible) {
                TextInputComponent(
                    onInput = onInput,
                    text = value,
                    containerColor = GreyBg,
                    onToggleVisibility = onToggleVisibility
                )
            }
        }
    }
}
