package com.example.noties.common.navigation

import com.example.noties.common.utils.MY_ARG

sealed class Screen(val route: String) {
    object NotesScreen : Screen("notes_screen")
    object MainScreen : Screen("main_screen")
    object SettingsScreen : Screen("settings_screen")
    object AddNotesScreen : Screen("add_notes_screen")
    object CameraScreen : Screen("camera_screen")
    object VideoScreen : Screen("video_screen")
    object GoogleScreen : Screen("google_screen")
    object DeepLinkScreen : Screen("deep_link_screen")
    object DeepLinkDetailScreen : Screen("deep_link_detail_screen")
    object DataStoreScreen : Screen("datastore_screen")
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