package com.example.noties.feature.presentation.add_edit_notes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.noties.ui.theme.Purple500

@Composable
fun EditTopBar(
    onTimePickClick: () -> Unit,
    onDeleteClick: () -> Unit,
    takePictureClick: @Composable () -> Unit
) {
    var takePicture by remember { mutableStateOf(false) }

    if (takePicture) {
        takePictureClick()
    }
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Purple500,
        title = {
            Text(text = "Note")
        },
        actions = {
            IconButton(
                onClick = { onTimePickClick() }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "")
            }


            IconButton(
                onClick = { onDeleteClick() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "")
            }

            IconButton(
                onClick = { takePicture = !takePicture }) {
                Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = "")
            }

        }
    )
}