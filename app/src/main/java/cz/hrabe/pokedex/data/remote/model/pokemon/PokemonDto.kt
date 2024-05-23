package cz.hrabe.pokedex.data.remote.model.pokemon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import cz.hrabe.pokedex.data.local.PokemonEntity
import cz.hrabe.pokedex.data.remote.model.pokemon.sprite.Sprites
import cz.hrabe.pokedex.data.remote.model.pokemon.type.TypeDto

@JsonClass(generateAdapter = true)
data class PokemonDto(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val height: Int,
    val weight: Int,
    val types: List<TypeDto>,
    @Json(name = "base_experience")
    val baseExperience: Int
)

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