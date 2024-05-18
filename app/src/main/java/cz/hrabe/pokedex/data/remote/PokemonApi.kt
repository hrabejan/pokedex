package cz.hrabe.pokedex.data.remote

import cz.hrabe.pokedex.data.remote.model.pokemon.PokemonDto
import cz.hrabe.pokedex.data.remote.model.pokemon.listing.PokemonListingDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun fetchPokemonListing(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListingDto

    @GET("pokemon/{name}")
    suspend fun fetchPokemonByName(@Path("name") name: String): PokemonDto

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}