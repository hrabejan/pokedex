package cz.hrabe.pokedex.domain.model

/**
 * UI query model of a Pokemon and it's color scheme
 */
data class PokemonWithColors(
    val pokemon: Pokemon,
    val pokemonColors: PokemonColors?
)
