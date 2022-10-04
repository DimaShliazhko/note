package com.example.noties.feature.presentation.notes

import com.example.noties.common.base.State
import com.example.noties.feature.domain.model.Note
import com.example.noties.feature.domain.model.SortType

data class NotesState(
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val sortType: SortType= SortType()
) : State {
}