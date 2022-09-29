package com.example.noties.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FontSize(
    val default: TextUnit = 10.sp,
    val extraSmall: TextUnit = 8.sp,
    val small: TextUnit = 10.sp,
    val medium: TextUnit = 16.sp,
    val large: TextUnit = 20.sp,
    val extraLarge: TextUnit = 64.sp,
)

val LocalFontSize = compositionLocalOf { FontSize() }

val MaterialTheme.fontSize: FontSize
    @Composable
    @ReadOnlyComposable
    get() = LocalFontSize.current