package com.example.noties.feature.presentation.notes.deep_link

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.TaskStackBuilder
import androidx.navigation.NavHostController
import com.example.noties.R
import com.example.noties.common.analytic.AnalyticsLoggerIml
import com.example.noties.common.navigation.Screen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DeepLinkScreen(
    navController: NavHostController,
) {

    val context = LocalContext.current
    val analyticsLogger = AnalyticsLoggerIml()
    val lifecycleOwner = LocalLifecycleOwner.current
    var second by remember { mutableStateOf("00") }
    var minute by remember { mutableStateOf("00") }
    var hour by remember { mutableStateOf("00") }

    val infiniteTransition = rememberInfiniteTransition()

    val infiniteSize by infiniteTransition.animateFloat(
        initialValue = 40f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val infiniteAlfa by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    DisposableEffect(key1 = lifecycleOwner) {
        analyticsLogger.registrationLifecycleOwner(
            navController.currentBackStackEntry?.destination?.route,
            lifecycleOwner
        )
        onDispose {
            analyticsLogger.removeLifecycleOwner(lifecycleOwner)
        }
    }
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                CustomAnimate(hour)
                Text(
                    text = " : ",
                    style = TextStyle(fontSize = MaterialTheme.typography.h3.fontSize),
                    textAlign = TextAlign.Center
                )
                CustomAnimate(minute)
                Text(
                    text = " : ",
                    style = TextStyle(fontSize = MaterialTheme.typography.h3.fontSize),
                    textAlign = TextAlign.Center
                )
                CustomAnimate(second)
            }
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(90f.dp)) {

                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(infiniteSize.dp),
                    backgroundColor = Color.Green.copy(alpha = infiniteAlfa)
                ) {}
                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(40.dp),
                    backgroundColor = Color.Blue
                ) {}
            }


            CanvasSample(
                second = { second = it.toString() },
                minute = { minute = it.toString() },
                hour = { hour = it.toString() })

        }


    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomAnimate(state: Any) {
    AnimatedContent(
        targetState = state,
        transitionSpec = {
            addAnimation().using(SizeTransform(clip = true))
        }) { count ->
        Text(
            text = if (count.toString().length > 1) {
                "$count"
            } else {
                "0$count"
            },
            style = TextStyle(fontSize = MaterialTheme.typography.h3.fontSize),
            textAlign = TextAlign.Center
        )
    }
}
