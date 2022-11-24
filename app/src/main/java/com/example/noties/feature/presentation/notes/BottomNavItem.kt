package com.example.noties.feature.presentation.notes

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.noties.R
import com.example.noties.common.navigation.Screen


enum class BottomNavItems(
    @StringRes val title: Int, val icon: ImageVector, val route: String
) {
    NoteScreen(R.string.home, Icons.Default.Home, Screen.NotesScreen.route),
    Settings(R.string.settings, Icons.Default.Settings, Screen.SettingsScreen.route),
}
