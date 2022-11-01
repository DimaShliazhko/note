package com.example.noties.feature.presentation.notes.deep_link

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.TaskStackBuilder
import androidx.navigation.NavHostController
import com.example.noties.R
import com.example.noties.common.navigation.Screen

@Composable
fun DeepLinkScreen(
    navController: NavHostController,
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(onClick = {
                navController.navigate(Screen.DeepLinkDetailScreen.route)
            }) {
                Text(text = stringResource(id = R.string.deep_link_detail))
            }
            Spacer(modifier = Modifier.height(16.dp))
            EmojiSlider()
            Button(onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://t.me/")
                )
                val pendingIntent = TaskStackBuilder.create(context).run {
                    addNextIntentWithParentStack(intent)
                    getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                }
                pendingIntent?.send()
            }) {
                Text(text = stringResource(id = R.string.deep_link_telegram))
            }
        }


    }
}