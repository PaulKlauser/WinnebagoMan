package com.paulklauser.winnebagoman.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val ColorPalette = darkColors(
    primary = ButtonGray,
    primaryVariant = ButtonGray,
    secondary = ButtonGray
)

@Composable
fun WinnebagoManTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}