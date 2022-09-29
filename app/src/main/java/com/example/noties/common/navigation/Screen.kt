package com.example.noties.common.navigation

sealed class Screen(val route: String) {
    object NotesScreen : Screen("notes_screen")
    object AddNotesScreen : Screen("add_notes_screen")
    object EditScreen : Screen("edit_screen")
}