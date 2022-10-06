package com.example.noties.feature.presentation.notes.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.noties.R

@Composable
fun DrawerMenu(
    modifier: Modifier = Modifier,
    allDeleteClick: () -> Unit,
    throwErrorClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
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
    }
}