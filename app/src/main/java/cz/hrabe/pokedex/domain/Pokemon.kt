package cz.hrabe.pokedex.domain

import androidx.compose.ui.graphics.Color

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val averageColor: Int?
)