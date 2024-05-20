package cz.hrabe.pokedex.domain.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

fun Color.getContrastColor(): Color {
    val luminance = ColorUtils.calculateLuminance(this.toArgb())
    return if (luminance < 0.5) Color.White else Color.Black
}