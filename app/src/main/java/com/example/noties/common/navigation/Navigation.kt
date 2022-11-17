package com.example.noties.common.navigation

import android.content.Intent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
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
import com.example.noties.feature.presentation.notes.camera.CameraScreen
import com.example.noties.feature.presentation.notes.datastore.DataStoreScreen
import com.example.noties.feature.presentation.notes.deep_link.DeepLinkDetailScreen
import com.example.noties.feature.presentation.notes.deep_link.DeepLinkScreen
import com.example.noties.feature.presentation.notes.login_google.GoogleScreen
import com.example.noties.feature.presentation.notes.video.VideoScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    darkMode: (isDark: Boolean) -> Unit) {


AnimatedNavHost(
navController = navController,
startDestination = Screen.NotesScreen.route,
) {
    composable(
        route = Screen.NotesScreen.route,
        exitTransition = null,
        enterTransition = null
    ) {
        NotesScreen(navController = navController, darkMode = {darkMode(it)})
    }

    composable(
        route = Screen.EditScreen.route,
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
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

    composable(
        route = Screen.CameraScreen.route,
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        }
    ) {
        navController.previousBackStackEntry?.savedStateHandle?.get<Long>(ID_BACK_STACK)?.let {
            CameraScreen(noteId = it, navController = navController)
        }

    }

    composable(
        route = Screen.VideoScreen.route,
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        }
    ) {
        VideoScreen(navController = navController)
    }

    composable(
        route = Screen.GoogleScreen.route,
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        }
    ) {
        GoogleScreen(navController = navController)
    }

    composable(
        route = Screen.DeepLinkScreen.route,
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        }
    ) {
        DeepLinkScreen(navController = navController)
    }

    composable(
        route = Screen.DataStoreScreen.route,
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        }
    ) {
        DataStoreScreen(navController = navController)
    }

    composable(
        route = Screen.DeepLinkDetailScreen.route,
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
        },
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "https://noties.com/{id}"
                action = Intent.ACTION_VIEW
            }
        ),
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) { entry ->
        DeepLinkDetailScreen(
            id = entry.arguments?.getInt("id") ?: 1,
            navController = navController
        )
    }
}

}

const val ID_BACK_STACK = "ID"