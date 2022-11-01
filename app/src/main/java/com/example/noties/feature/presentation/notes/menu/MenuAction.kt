package com.example.noties.feature.presentation.notes.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.noties.R


sealed class MenuAction(@StringRes val title: Int, @DrawableRes val icon: Int) {
    object Search : MenuAction(R.string.search, R.drawable.ic_search)
    object Sort : MenuAction(R.string.sort, R.drawable.ic_sort)
}