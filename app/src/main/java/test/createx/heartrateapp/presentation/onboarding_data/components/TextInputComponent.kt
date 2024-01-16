package test.createx.heartrateapp.presentation.onboarding_data.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputComponent() {
    TextField(
        placeholder = { Text(text = "Your name") },
        value = "",
        onValueChange = { },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = RedAction,
            containerColor = White,
            placeholderColor = GreySubText,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        textStyle = MaterialTheme.typography.titleSmall,
        shape = RoundedCornerShape(10.dp)
    )
}