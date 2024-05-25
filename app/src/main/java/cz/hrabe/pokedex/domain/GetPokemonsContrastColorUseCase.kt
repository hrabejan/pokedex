package cz.hrabe.pokedex.domain

import androidx.annotation.ColorInt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

class GetPokemonsContrastColorUseCase {

    operator fun invoke(
        @ColorInt color: Int,
        @ColorInt onDark: Int = Color.White.toArgb(),
        @ColorInt onLight: Int = Color.Black.toArgb()
    ): Int {
        val luminance = ColorUtils.calculateLuminance(color)
        return if (luminance < 0.5) onDark else onLight
    }
}