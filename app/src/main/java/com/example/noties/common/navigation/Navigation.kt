package com.example.noties.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.noties.common.utils.MY_ARG
import com.example.noties.common.utils.MY_URI
import com.example.noties.common.utils.NOTE_ID
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

        composable(
            route = Screen.EditScreen.route,
            arguments = listOf(navArgument(MY_ARG) { type = NavType.LongType }),
            deepLinks = listOf(navDeepLink { uriPattern = "$MY_URI/$MY_ARG={$MY_ARG}" })
        ) {

            it.arguments?.getLong(MY_ARG)?.let { id ->
                EditNoteScreen(navController = navController, id)
            }

            navController.previousBackStackEntry?.savedStateHandle?.get<Long>(NOTE_ID)?.let {
                EditNoteScreen(navController = navController, it)
            }
        }

        composable(Screen.AddNotesScreen.route) {
            EditNoteScreen(navController = navController)
        }
    }
}