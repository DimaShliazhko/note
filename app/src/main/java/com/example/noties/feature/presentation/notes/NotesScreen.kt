package com.example.noties.feature.presentation.notes

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noties.common.navigation.Screen
import com.example.noties.common.utils.NOTE_ID
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.presentation.notes.drawer.DrawerMenu
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
    darkMode: (isDark: Boolean) -> Unit
) {
    val state = viewModel.state.value
    val action = viewModel.action
    val listState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var isSortOpen by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = action) {
        viewModel.action.collect {
            when (it) {
                is NotesAction.ScrollListUp -> {
                    scope.launch { listState.animateScrollToItem(0) }
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.onBackground),
        topBar = {
            TopBar(onTextChange = {
                viewModel.setEvent(NotesEvent.Search(it))
            },
                onSortClick = {
                    isSortOpen = it
                },
                navigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
        },
        drawerContent = {
            DrawerMenu(
                allDeleteClick = { viewModel.setEvent(NotesEvent.DeleteAllNote) },
                throwErrorClick = { throw Error("something wrong happened") },
                openVideoScreenClick = { navController.navigate((Screen.VideoScreen.route)) },
                openGoogleScreenClick = { navController.navigate((Screen.GoogleScreen.route)) },
                openDeepLinkScreenClick = { navController.navigate((Screen.DeepLinkScreen.route)) },
                darkMode = { darkMode(it) },
                openDataStoreScreenClick = { navController.navigate((Screen.DataStoreScreen.route)) }
            )
        },
        floatingActionButton = {
            if (!listState.isScrollInProgress) {
                FloatingActionButton(
                    modifier = Modifier.padding(vertical = 20.dp),
                    onClick = {
                        navController.navigate(Screen.AddNotesScreen.route)
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add note")
                }
            }
        },
        scaffoldState = scaffoldState
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = viewModel.state.value.isLoading),
                onRefresh = {
                    viewModel.setEvent(NotesEvent.Refresh)
                    scope.launch { listState.animateScrollToItem(0) }
                }) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.onBackground)
                ) {
                    AnimatedVisibility(
                        visible = isSortOpen,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically(),
                    ) {
                        SortSection(
                            viewModel = viewModel,
                            onSort = {
                                viewModel.setEvent(NotesEvent.SortNote(it))
                            })
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = listState

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

                        items(state.notes, key = { it }) { note ->
                            if (state.searchText.let { it1 -> note.title.contains(it1) }) {
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
                    }
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center), color = Color.Red
                )
            }
        }
    }
}