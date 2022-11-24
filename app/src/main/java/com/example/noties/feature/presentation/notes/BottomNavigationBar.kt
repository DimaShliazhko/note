package com.example.noties.feature.presentation.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable

fun BottomNavigationBar(
    navController: NavController,
    selectorTab : BottomNavItems,
    onItemClickListener: (item: BottomNavItems) -> Unit
) {

    //  val backStackEntry = navController.currentBackStackEntryAsState()

    val items = listOf(BottomNavItems.NoteScreen, BottomNavItems.Settings)

    BottomNavigation() {
        items.forEach { item ->
            val selected = selectorTab.route == item.route
            BottomNavigationItem(
                modifier = Modifier.navigationBarsPadding(),
                selected = selected,
                selectedContentColor = Color.DarkGray,
                unselectedContentColor = Color.LightGray,
                onClick = { onItemClickListener(item) },
                icon = {
                    Column() {
                        Icon(imageVector = item.icon, contentDescription = item.route)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(id = item.title),
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }
    }
}