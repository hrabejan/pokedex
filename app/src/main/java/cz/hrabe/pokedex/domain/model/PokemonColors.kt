package cz.hrabe.pokedex.domain.model

import androidx.compose.ui.graphics.Color

/**
 * Base UI model for Pokemon's color scheme
 */
class PokemonColors(
    /**
     * Average color of Pokemon's downloaded image
     */
    val averageColor: Color,
    /**
     * Contrast color of [averageColor]
     */
    val contrastColor: Color
)