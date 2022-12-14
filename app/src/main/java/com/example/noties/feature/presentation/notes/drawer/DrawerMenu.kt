package com.example.noties.feature.presentation.notes.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.noties.R

@Composable
fun DrawerMenu(
    modifier: Modifier = Modifier,
    allDeleteClick: () -> Unit,
    throwErrorClick: () -> Unit,
    openVideoScreenClick: () -> Unit,
    openGoogleScreenClick: () -> Unit,
    openDeepLinkScreenClick: () -> Unit,
    openDataStoreScreenClick: () -> Unit,
    openQrScreenClick: () -> Unit,
    openPermissionScreenClick: () -> Unit,
    darkMode: (isDarck: Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(vertical = 20.dp)
    ) {

        Switch(checked = checked,
            onCheckedChange = {
                checked = !checked
                darkMode(checked)
            }
        )
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = { allDeleteClick() }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "note delete all")
            Text(
                text = stringResource(id = R.string.delete_all_note),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = { throwErrorClick() }) {
            Icon(imageVector = Icons.Default.Error, contentDescription = "note delete all")
            Text(
                text = stringResource(id = R.string.throw_error),
            )
        }

        TextButton(
            onClick = { openVideoScreenClick() }) {
            Icon(imageVector = Icons.Default.Videocam, contentDescription = "note delete all")
            Text(
                text = stringResource(id = R.string.open_video_screen),
            )
        }
        TextButton(
            onClick = { openGoogleScreenClick() }) {
            Icon(
                imageVector = Icons.Default.Login,
                contentDescription = stringResource(id = R.string.open_google_button)
            )
            Text(
                text = stringResource(id = R.string.open_google_button),
            )
        }

        TextButton(
            onClick = { openDeepLinkScreenClick() }) {
            Icon(
                imageVector = Icons.Default.ModeEdit,
                contentDescription = stringResource(id = R.string.open_deep_link)
            )
            Text(
                text = stringResource(id = R.string.open_deep_link),
            )
        }

        TextButton(
            onClick = { openDataStoreScreenClick() }) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = stringResource(id = R.string.open_datastore)
            )
            Text(
                text = stringResource(id = R.string.open_datastore),
            )
        }

        TextButton(
            onClick = { openQrScreenClick() }) {
            Icon(
                imageVector = Icons.Default.QrCode,
                contentDescription = stringResource(id = R.string.open_qrScreen)
            )
            Text(
                text = stringResource(id = R.string.open_qrScreen),
            )
        }

        TextButton(
            onClick = { openPermissionScreenClick() }) {
            Icon(
                imageVector = Icons.Default.RequestPage,
                contentDescription = stringResource(id = R.string.open_permission)
            )
            Text(
                text = stringResource(id = R.string.open_permission),
            )
        }
    }
}