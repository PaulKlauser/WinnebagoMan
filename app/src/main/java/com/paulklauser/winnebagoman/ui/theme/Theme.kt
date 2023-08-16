package com.paulklauser.winnebagoman.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val RetroColorPalette = darkColors(
    primary = ButtonGray,
    primaryVariant = ButtonGray,
    surface = ToolbarGray
)

private val ModernColorPalette = darkColors(
    primary = LightBlue,
    secondary = LightYellow,
    surface = DarkPurple,
    background = DarkPurple2
)

@Composable
fun WinnebagoManTheme(isRetroTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (isRetroTheme) RetroColorPalette else ModernColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}