package com.example.noties.feature.presentation.notes.deep_link

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DeepLinkDetailScreen(
    id: Int,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Detail Screen")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Id is $id")
        }
    }
}