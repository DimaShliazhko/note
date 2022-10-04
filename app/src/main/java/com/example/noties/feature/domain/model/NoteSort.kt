package com.example.noties.feature.domain.model

import androidx.annotation.StringRes
import com.example.noties.R

sealed class NoteSort(@StringRes val title: Int) {
    object Title : NoteSort(R.string.title)
    object Date : NoteSort(R.string.date)
}