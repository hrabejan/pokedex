package cz.hrabe.pokedex.data.remote.model.pokemon.listing

import com.squareup.moshi.JsonClass

/**
 * Pokemon listing contains the total amount of Pokemon available, offset for the next/previous request
 * and a list of pokemon with their bare-bone information.
 */
@JsonClass(generateAdapter = true)
data class PokemonListingDto(
    /**
     * Number of available Pokemon in server's database
     */
    val count: Int,
    /**
     * Next offset
     */
    val next: String?,
    /**
     * Previous offset
     */
    val previous: String?,
    /**
     * List of Pokemon items
     */
    val results: List<PokemonListingItemDto>
)