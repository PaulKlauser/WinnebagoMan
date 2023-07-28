package com.paulklauser.winnebagoman.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RetroColorPalette = darkColors(
    primary = ButtonGray,
    primaryVariant = ButtonGray,
    surface = ToolbarGray
)

private val ModernColorPalette = darkColors(
    primary = Color(0xFF67C6FF),
    secondary = Color(0xFFFFD9A9),
    surface = Color(0xFF5973FF),
    background = Color(0xFF273365)
)

@Composable
fun WinnebagoManTheme(isRetroTheme: Boolean, content: @Composable () -> Unit) {
    if (isRetroTheme) {
        MaterialTheme(
            colors = RetroColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    } else {
        MaterialTheme(
            colors = ModernColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}