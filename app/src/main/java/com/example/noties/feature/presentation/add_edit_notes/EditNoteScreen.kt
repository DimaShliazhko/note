package com.example.noties.feature.presentation.add_edit_notes

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.noties.common.extension.toDate
import com.example.noties.feature.domain.model.Note
import kotlinx.coroutines.launch

@Composable
fun EditNoteScreen(
    navController: NavHostController,
    note: Note? = null,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    if (note != null) {
        viewModel.setEvent(EditNoteEvent.LoadNote(note))
    }
    val context = LocalContext.current
    val state = viewModel._state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var title by remember {
        mutableStateOf(state.note?.title ?: "")
    }

    var content by remember {
        mutableStateOf(state.note?.content ?: "")
    }

    var colorAnimatable = remember {
        Animatable(Color(note?.color ?: Note.listColors.random().toArgb()))
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            EditTopBar(onTimePickClick = {
                TimePicker(context, onTimePick = { calendar ->

                    DatePicker(context, calendar, onDataPick = { calendar ->
                        viewModel.setEvent(EditNoteEvent.TimePick(calendar.timeInMillis))
                    })
                })
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.setEvent(
                        EditNoteEvent.SaveNote(
                            note = Note(
                                id = note?.id,
                                title = title,
                                content = content,
                                color = colorAnimatable.value.toArgb()
                            )
                        )
                    )
                    navController.navigateUp()
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = if (note == null) {
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
                                }
                            }
                    ) {

                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            state.notificationTime?.let {
                Text(text = it.toDate())
                Spacer(modifier = Modifier.height(16.dp))
            }
            TransparentHintTextField(
                text = title,
                hint = "Title here...",
                onValueChange = {
                    title = it
                },
                isHintVisible = title.isEmpty(),
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = content,
                hint = "Content here...",
                onValueChange = {
                    content = it
                },
                isHintVisible = content.isEmpty(),
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
