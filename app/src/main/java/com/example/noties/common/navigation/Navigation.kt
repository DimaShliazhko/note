package com.example.noties.common.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.noties.common.utils.MY_ARG
import com.example.noties.common.utils.MY_URI
import com.example.noties.common.utils.NOTE_ID
import com.example.noties.feature.presentation.add_edit_notes.EditNoteScreen
import com.example.noties.feature.presentation.notes.NotesScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.NotesScreen.route,
    ) {
        composable(
            Screen.NotesScreen.route
        ) {
            NotesScreen(navController = navController)
        }

        composable(
            route = Screen.EditScreen.route,
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700))
            },
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700))
            },
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

        composable(
            Screen.AddNotesScreen.route,
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            }
        ) {
            EditNoteScreen(navController = navController)
        }
    }
}