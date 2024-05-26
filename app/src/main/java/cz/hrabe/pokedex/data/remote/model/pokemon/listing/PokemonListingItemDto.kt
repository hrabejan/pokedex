package cz.hrabe.pokedex.data.remote.model.pokemon.listing

/**
 * Pokemon's listing item includes only it's name and url for further data.
 */
data class PokemonListingItemDto(val name: String, val url: String)