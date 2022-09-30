package com.example.noties.feature.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noties.common.navigation.Screen
import com.example.noties.common.utils.NOTE_ID
import com.example.noties.feature.domain.model.Note
import com.example.noties.ui.theme.Purple500
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddNotesScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add note")
            }
        },
        scaffoldState = scaffoldState
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Purple500)
            ) {
                if (state.isLoading) {
                    items(20) {
                        NoteItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                },
                            note = Note(id = 1, "", "", Color.Gray.copy(alpha = 0.4f).toArgb()),
                            isLoading = state.isLoading,
                            onDeleteClick = {
                            }
                        )
                    }
                }
                items(state.notes) { note ->
                    NoteItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.currentBackStackEntry?.savedStateHandle?.set(NOTE_ID, note.id)
                                navController.navigate(Screen.EditScreen.passNoteId(note.id!!))
                            },
                        note = note,
                        isLoading = state.isLoading,
                        onDeleteClick = {
                            viewModel.setEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note Delete",
                                    actionLabel = "Cancel"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.setEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center), color = Color.Red)
            }
        }
    }
}