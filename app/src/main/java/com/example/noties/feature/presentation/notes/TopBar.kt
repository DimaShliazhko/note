package com.example.noties.feature.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import com.example.noties.R
import com.example.noties.feature.presentation.notes.menu.MenuAction
import com.example.noties.ui.theme.Purple500

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    onSortClick: (Boolean) -> Unit
) {
    var onSearchClick by remember { mutableStateOf(false) }
    var onSort by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = Purple500,
        title = {
            if (onSearchClick) {
                SearchAppBar(
                    onTextChange = {
                        onTextChange(it)
                    },
                    onCloseClicked = {
                        onSearchClick = !onSearchClick
                        onTextChange("")
                    },
                    onSearchClicked = { setings -> })
            } else {
                Text(text = stringResource(id = R.string.all_noties))
            }
        },
        actions = {
            if (!onSearchClick) {
                IconButton(onClick = { onSearchClick = true }) {
                    Icon(
                        painter = painterResource(MenuAction.Search.icon),
                        contentDescription = stringResource(
                            id = MenuAction.Search
                                .title
                        )
                    )
                }
            }
            IconButton(onClick = {
                onSort = !onSort
                onSortClick(onSort)

            }) {
                Icon(
                    painter = painterResource(MenuAction.Sort.icon),
                    contentDescription = stringResource(
                        id = MenuAction.Search
                            .title
                    )
                )
            }
        })
}


@Composable
fun SearchAppBar(
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = {
            onTextChange(it)
            text = it
        },
        placeholder = {
            Text(
                modifier = Modifier
                    .alpha(ContentAlpha.medium),
                text = "Search here...",
                color = Color.White
            )
        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        ),
        singleLine = true,
        leadingIcon = {
            IconButton(
                modifier = Modifier
                    .alpha(ContentAlpha.medium),
                onClick = {
                    onCloseClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        text = ""
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClicked(text)
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
        ))
}
