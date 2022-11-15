package com.example.noties.feature.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.noties.feature.domain.model.NoteSort
import com.example.noties.feature.domain.model.SortType

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    onSort: (SortType) -> Unit,
    viewModel: NotesViewModel
) {

    val state = viewModel.state.value
    var sortByTitle by remember { mutableStateOf(state.sortType.sortByTitle) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.onBackground),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(modifier = Modifier.padding(8.dp),
            selected = sortByTitle, onClick = {
                sortByTitle = !sortByTitle
                onSort(SortType(sortByTitle = sortByTitle,sortByDate = !sortByTitle))
            })
        Text(text = stringResource(id = NoteSort.Title.title))
        RadioButton(modifier = Modifier.padding(8.dp), selected = !sortByTitle,
            onClick = {
                sortByTitle = !sortByTitle
                onSort(SortType(sortByTitle = sortByTitle,sortByDate = !sortByTitle))
            })
        Text(text = stringResource(id = NoteSort.Date.title))
    }
}