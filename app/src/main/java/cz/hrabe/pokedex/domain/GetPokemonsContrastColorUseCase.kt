package cz.hrabe.pokedex.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

class GetPokemonsContrastColorUseCase {

    operator fun invoke(color: Color) : Color {
        val luminance = ColorUtils.calculateLuminance(color.toArgb())
        return if (luminance < 0.5) Color.White else Color.Black
    }
}