package com.example.noties

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.noties.common.navigation.Navigation
import com.example.noties.ui.theme.NotiesTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotiesTheme {
                val navController = rememberAnimatedNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Navigation(navController)
                }
            }
        }
    }
}

