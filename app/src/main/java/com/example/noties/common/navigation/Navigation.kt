package com.example.noties.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.presentation.add_edit_notes.EditNoteScreen
import com.example.noties.feature.presentation.notes.NotesScreen

@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.NotesScreen.route,
    ) {
        composable(Screen.NotesScreen.route) {
            NotesScreen(navController = navController)
        }

        composable(Screen.EditScreen.route) {
            val note = navController.previousBackStackEntry?.savedStateHandle?.get<Note>("note")
            EditNoteScreen(navController = navController, note)
        }

        composable(Screen.EditScreen.route) {
            val note = navController.previousBackStackEntry?.savedStateHandle?.get<Note>("note")
            EditNoteScreen(navController = navController, note)
        }

        composable(Screen.AddNotesScreen.route) {
            EditNoteScreen(navController = navController)
        }
    }
}