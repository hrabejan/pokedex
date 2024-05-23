package cz.hrabe.pokedex.data.local

import androidx.room.Embedded
import cz.hrabe.pokedex.domain.PokemonWithColors

data class PokemonWithColorsEntity(
    @Embedded val pokemonEntity: PokemonEntity,
    @Embedded val pokemonColorEntity: PokemonColorEntity?
)

fun PokemonWithColorsEntity.toPokemonWithColors(): PokemonWithColors {
    return PokemonWithColors(
        this.pokemonEntity.toPokemon(),
        this.pokemonColorEntity?.toPokemonColors()
    )
}