package com.example.noties

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.noties.common.navigation.Navigation
import com.example.noties.feature.presentation.notes.TopBar
import com.example.noties.ui.theme.NotiesTheme
import com.example.noties.ui.theme.Purple500
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotiesTheme {
                val navController = rememberNavController()
                Scaffold(

                ) {
                    Navigation(navController)
                }
            }
        }
    }
}

