package cz.hrabe.pokedex.data.remote

import cz.hrabe.pokedex.data.remote.model.pokemon.PokemonDto
import cz.hrabe.pokedex.data.remote.model.pokemon.listing.PokemonListingDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface defining basic functions for fetching Pokemon
 */
interface PokemonApi {

    /**
     * Fetches all Pokemon [limit] of Pokemon starting from ID [offset] (exclusive, first Pokemon's ID is 1)
     *
     * @param limit how many Pokemon per request
     * @param offset ID offset of requested Pokemon (exclusive)
     */
    @GET("pokemon/")
    suspend fun fetchPokemonListing(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListingDto

    /**
     * Fetches a single Pokemon from the API, whose name matches the argument
     *
     * @param name searched Pokemon's name
     */
    @GET("pokemon/{name}")
    suspend fun fetchPokemonByName(@Path("name") name: String): PokemonDto

    companion object {
        /**
         * Base URL for fetching Pokemon data
         */
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}