package cz.hrabe.pokedex.data.remote.model.pokemon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import cz.hrabe.pokedex.data.local.model.PokemonEntity
import cz.hrabe.pokedex.data.remote.model.pokemon.sprite.Sprites
import cz.hrabe.pokedex.data.remote.model.pokemon.type.TypeDto

/**
 * A more detailed network model of a Pokemon
 */
@JsonClass(generateAdapter = true)
data class PokemonDto(
    /**
     * Pokemon's identifier
     */
    val id: Int,
    /**
     * Pokemon's name
     */
    val name: String,
    /**
     * Pokemon's sprites (image urls)
     */
    val sprites: Sprites,
    /**
     * Pokemon's height in decimeters
     */
    val height: Int,
    /**
     * Pokemon's weight in hectograms
     */
    val weight: Int,
    /**
     * Pokemon's types
     */
    val types: List<TypeDto>,
    /**
     * Base experience for defeating this pokemon
     */
    @Json(name = "base_experience")
    val baseExperience: Int
)

/**
 * Mapping extension function.
 *
 * Maps a PokemonDto object to a PokemonEntity database model.
 */
fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        this.id,
        this.name,
        this.sprites.other.officialArtwork.front_default,
        this.height,
        this.weight,
        this.types.map { it.type.name },
        this.baseExperience
    )
}