package com.example.noties.feature.presentation.notes.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.noties.R

@Composable
fun PhotoCameraPicker(
    pickGallery: () -> Unit,
    pickTakePhoto: () -> Unit
) {
    Dialog(onDismissRequest = { }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = 8.dp
        ) {


            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = stringResource(id = R.string.choose_title),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(id = R.string.choose),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1
                )


                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(MaterialTheme.colors.onBackground),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(onClick = { pickGallery() }) {
                        Text(text = stringResource(id = R.string.pick_gallery))
                    }
                    TextButton(onClick = { pickTakePhoto() }) {
                        Text(text = stringResource(id = R.string.pick_photo))
                    }
                }
            }

        }
    }
}