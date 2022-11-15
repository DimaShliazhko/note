package com.example.noties.feature.presentation.notes

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.noties.common.extension.toDate
import com.example.noties.feature.domain.model.Note
import com.example.noties.ui.theme.DarkGreen
import com.example.noties.ui.theme.fontSize
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    isLoading: Boolean,
    onDeleteClick: () -> Unit
) {

    /*   val offsetX by animateFloatAsState(
           targetValue = 150f,
           animationSpec = tween(1000, easing = LinearEasing),
       )*/

    var offsetX by remember { mutableStateOf(0f) }
    var startOffset by remember { mutableStateOf(false) }

    val offsetXX by animateFloatAsState(
        targetValue = if (!startOffset) 0f else offsetX,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
    )


    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ActionsRow(
            actionIconSize = 50.dp,
            onDelete = {},
            onEdit = {},
            onFavorite = {},
        )


        Card(
            modifier = modifier
                .fillMaxWidth()
                .offset(offsetXX.dp)
                .padding(vertical = 10.dp, horizontal = 15.dp)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        if (delta >= 0) {
                            offsetX = if (delta <= 150) {
                                delta
                            } else {
                                150f
                            }
                        } else {
                            offsetX = 0f
                        }

                    },
                    onDragStarted = { startOffset = true },
                    onDragStopped = {
                        if (offsetX > 0) offsetX = 150f else offsetX = 0f
                    },
                ),
            shape = RoundedCornerShape(10.dp),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(note.color))
                    .padding(16.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(
                            visible = isLoading,
                            color = Color.Gray.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(8.dp),
                            highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                        ),
                    text = note.title,
                    maxLines = 1,
                    fontSize = MaterialTheme.fontSize.large,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(
                            visible = isLoading,
                            color = Color.Gray.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(8.dp),
                            highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                        ),
                    maxLines = 3,
                    text = note.content,
                    fontSize = MaterialTheme.fontSize.medium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(
                            visible = isLoading,
                            color = Color.Gray.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(8.dp),
                            highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                        ),
                ) {
                    if (note.notificationTime != null) {
                        TextButton(onClick = { }) {
                            Icon(imageVector = Icons.Default.Notifications, contentDescription = "note icon")
                            Text(
                                text = note.notificationTime?.toDate() ?: "",
                                color = if ((note.notificationTime ?: 0) > System.currentTimeMillis()) {
                                    DarkGreen
                                } else {
                                    Color.Red
                                }
                            )
                        }
                    }

                    IconButton(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        onClick = { onDeleteClick() }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "delete icon")
                    }
                }
            }
        }
    }
}