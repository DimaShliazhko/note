package com.example.noties.feature.presentation.notes.camera

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.noties.ui.theme.Purple500

@Composable
fun CameraTopBar(
    onSaveClick: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Purple500,
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