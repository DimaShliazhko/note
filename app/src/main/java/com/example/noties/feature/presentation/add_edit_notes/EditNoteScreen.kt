package com.example.noties.feature.presentation.add_edit_notes

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.noties.R
import com.example.noties.common.extension.toDate
import com.example.noties.feature.domain.model.Note
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditNoteScreen(
    navController: NavHostController,
    noteId: Long? = null,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = noteId) {
        if (noteId != null) {
            viewModel.setEvent(EditNoteEvent.LoadNote(noteId))
        }
    }
    val context = LocalContext.current
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var colorAnimatable = remember {
        Animatable(Color(state.color))
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            EditTopBar(
                onTimePickClick = {
                    TimePicker(
                        context,
                        onTimePick = { calendarTime ->
                            DatePicker(context, calendarTime, onDataPick = { calendarData ->
                                viewModel.setEvent(EditNoteEvent.TimePick(calendarData.timeInMillis))
                            })
                        }
                    )
                },
                onDeleteClick = {
                    viewModel.setEvent(EditNoteEvent.DeleteNote)
                    navController.navigateUp()
                }

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.setEvent(
                        EditNoteEvent.SaveNote
                    )
                    navController.navigateUp()
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = if (noteId == null) {
                        Icons.Default.Send
                    } else {
                        Icons.Default.Edit
                    }, contentDescription = " edit note"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.listColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .background(color)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = if (colorInt == colorAnimatable.value.toArgb()) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                },
                                shape = CircleShape

                            )
                            .clickable {
                                scope.launch {
                                    colorAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(durationMillis = 500)
                                    )
                                    viewModel.setEvent(EditNoteEvent.AddColor(colorAnimatable.value.toArgb()))
                                }
                            }
                    ) {
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            state.notificationTime?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector =  ImageVector.vectorResource(R.drawable.ic_time), contentDescription ="timer")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = it.toDate())
                    IconButton(onClick = { viewModel.setEvent(EditNoteEvent.DeleteTimer) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "delete timer")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
            TransparentHintTextField(
                text = state.title,
                hint = "Title here...",
                onValueChange = {
                    viewModel.setEvent(EditNoteEvent.AddTitle(it))
                },
                isHintVisible = state.title.isEmpty(),
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
                onFocusChange = {
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = state.content,
                hint = "Content here...",
                onValueChange = {
                    viewModel.setEvent(EditNoteEvent.AddContent(it))
                },
                isHintVisible = state.content.isEmpty(),
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight(),
                onFocusChange = {
                }
            )
        }
    }
}
