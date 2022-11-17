package com.example.noties.feature.presentation.notes.datastore

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.noties.R
import com.example.noties.ui.theme.fontSize

@Composable
fun DataStoreScreen(
    modifier: Modifier = Modifier,
    viewModel: DataStoreViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    var text by remember { mutableStateOf("") }
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text(text = stringResource(id = R.string.input_text)) }
            )
            Spacer(modifier = modifier.height(16.dp))

            TextButton(
                modifier = modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black), shape = RoundedCornerShape
                            (20.dp)
                    ),
                onClick = { viewModel.setEvent(DataStoreEvent.SetValue(text)) }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = ""
                )
                Text(
                    text = stringResource(id = R.string.save),
                )
            }

            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = "Value",
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = state.firstName,
                fontSize = MaterialTheme.fontSize.large
            )
        }

    }
    if (state.isLoad) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp),
        )
    }

}