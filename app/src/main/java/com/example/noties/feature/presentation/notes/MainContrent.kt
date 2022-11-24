package com.example.noties.feature.presentation.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.noties.feature.presentation.notes.settings.SettingsScreen
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier


@Composable
fun MainContent(
    navController: NavController,
    darkMode: (isDark: Boolean) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    var selector by remember { mutableStateOf(BottomNavItems.NoteScreen) }
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.primarySurface,
        topBar = {

        },
        bottomBar = {
            BottomNavigationBar(navController = navController, selectorTab = selector, onItemClickListener = {
                selector =
                    it
            })
        },

        ) {
        when (selector) {
            BottomNavItems.NoteScreen -> {
                NotesScreen(navController = navController, darkMode = { darkMode(it) })
            }
            BottomNavItems.Settings -> SettingsScreen()
        }
    }
}

