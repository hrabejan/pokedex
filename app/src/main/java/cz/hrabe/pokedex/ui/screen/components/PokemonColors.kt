package cz.hrabe.pokedex.ui.screen.components

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class PokemonColors(val avgColor: Color = Color.White, val contrastColor: Color = Color.Black)

val LocalPokemonColors = compositionLocalOf { PokemonColors() }