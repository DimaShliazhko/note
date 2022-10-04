package com.example.noties.feature.presentation.notes

import com.example.noties.common.base.Action

sealed class NotesAction : Action {
    object  ScrollListUp :NotesAction()
}