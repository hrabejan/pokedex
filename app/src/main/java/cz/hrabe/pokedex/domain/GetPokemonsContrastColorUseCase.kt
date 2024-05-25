package cz.hrabe.pokedex.domain

import androidx.annotation.ColorInt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

class GetPokemonsContrastColorUseCase {

    /**
     * Returns the [onDark] or [onLight] ColorInt based on the luminance of [color].
     * If the luminance equal or bigger than 0.5, [onLight] is returned, otherwise [onDark].
     *
     * @param color color to get the contrast color for
     * @param onDark return value in case of dark luminance
     * @param onLight return value in case of light luminance
     */
    operator fun invoke(
        @ColorInt color: Int,
        @ColorInt onDark: Int = Color.White.toArgb(),
        @ColorInt onLight: Int = Color.Black.toArgb()
    ): Int {
        val luminance = ColorUtils.calculateLuminance(color)
        return if (luminance < 0.5) onDark else onLight
    }
}