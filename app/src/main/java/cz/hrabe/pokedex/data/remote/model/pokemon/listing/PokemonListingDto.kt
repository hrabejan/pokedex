package cz.hrabe.pokedex.data.remote.model.pokemon.listing

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListingDto(
    val count: Int, val next: String?,
    val previous: String?,
    val results: List<PokemonListingItemDto>
)