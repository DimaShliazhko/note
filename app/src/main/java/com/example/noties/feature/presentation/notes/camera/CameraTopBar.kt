package com.example.noties.feature.presentation.notes.camera

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun CameraTopBar(
    onSaveClick: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.onBackground,
        title = {
            Text(text = "Camera")
        },
        actions = {
            IconButton(
                onClick = { onSaveClick() }) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "")
            }
        }
    )
}