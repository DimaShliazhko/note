package com.example.noties.feature.presentation.notes.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.noties.R

@Composable
fun ErrorDialog(
    throwable: Throwable,
    onDismiss: (Boolean) -> Unit
) {
    AlertDialog(
        modifier = Modifier.clip(RoundedCornerShape(10.dp)),
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = { onDismiss(true) }) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
        },
        title = {
            Text(text = stringResource(id = R.string.error))
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Ups...")
                Text(text = "${throwable.message}")
            }
        }
    )
}