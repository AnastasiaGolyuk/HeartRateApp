package test.createx.heartrateapp.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import test.createx.heartrateapp.ui.theme.BlackMain
import test.createx.heartrateapp.ui.theme.GreySubText
import test.createx.heartrateapp.ui.theme.RedMain
import test.createx.heartrateapp.ui.theme.White

@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String?=null,
    confirmButtonText: String
) {
    AlertDialog(
        containerColor = White,
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.labelLarge,
                color = BlackMain,
                textAlign = TextAlign.Start
            )
        },
        text = {
            if (dialogText != null) {
                Text(
                    text = dialogText,
                    style = MaterialTheme.typography.bodySmall,
                    color = GreySubText,
                    textAlign = TextAlign.Start
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    text = confirmButtonText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = RedMain
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.bodyMedium,
                    color = RedMain
                )
            }
        }
    )
}