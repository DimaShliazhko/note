package com.example.noties.common.navigation

import com.example.noties.common.utils.MY_ARG

sealed class Screen(val route: String) {
    object NotesScreen : Screen("notes_screen")
    object AddNotesScreen : Screen("add_notes_screen")
    object CameraScreen : Screen("camera_screen")
    object EditScreen : Screen("edit_screen/{$MY_ARG}") {
        fun passNoteId(noteId: Long) = "edit_screen/$noteId"
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { args ->
                append("/$args")
            }
        }
    }
}