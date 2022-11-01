package com.example.noties.feature.presentation.notes.login_google

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.noties.R
import com.example.noties.ui.theme.Shapes

@Composable
fun GoogleScreen(
    navController: NavHostController,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleButton()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoogleButton(
    text: String = stringResource(id = R.string.sign_google),
    loadingText: String = stringResource(id = R.string.create_account)
) {
    var onClick by remember { mutableStateOf(false) }
    Surface(
        onClick = { onClick = !onClick },
        shape = Shapes.medium,
        border = BorderStroke(width = 1.dp, color = Color.Red),
        color = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                .animateContentSize(
                    animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(
                    id = if (!onClick) {
                        R.string.sign_google
                    } else {
                        R.string.create_account
                    }
                )
            )
            if (onClick) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}


@Composable
@Preview
fun GoogleButtonPreview() {
    GoogleButton()
}