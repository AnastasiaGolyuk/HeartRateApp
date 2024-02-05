package test.createx.heartrateapp.presentation.onboarding_data.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.onboarding_data.OnboardingDataViewModel
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TextInputComponent(screenViewModel: OnboardingDataViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var text by remember {
        mutableStateOf(screenViewModel.user.value.name)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(text) {
        isError = if (isValidText(text)) {
                screenViewModel.onNameChange(text)
                false
            } else  {
                if (text.isNotEmpty()) {
                    true
                } else {
                    screenViewModel.onNameChange(text)
                    false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        TextField(
            placeholder = { Text(text = "Your name") },
            value = text,
            onValueChange = {
                text = it
            },
            trailingIcon = {
                if (isError) {
                    Icon(
                        painter = painterResource(R.drawable.trailing_icon_error),
                        contentDescription = "error",
                        tint = RedAction
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.textFieldColors(
                textColor = RedAction,
                containerColor = White,
                placeholderColor = GreySubText,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.titleSmall,
            shape = RoundedCornerShape(10.dp)
        )
        if (isError) {
            Text(
                text = "Please use standard characters only - no symbols",
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.labelSmall,
                color = RedAction
            )
        }
    }
}

fun isValidText(text: String): Boolean {
    return text.matches(Regex("[a-zA-Z]+"))
}

