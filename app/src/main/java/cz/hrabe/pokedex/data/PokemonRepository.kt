package cz.hrabe.pokedex.data

import androidx.paging.PagingData
import cz.hrabe.pokedex.domain.model.Pokemon
import cz.hrabe.pokedex.domain.model.PokemonWithColors
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    /**
     * Returns a PagingData flow of Pokemon
     */
    fun fetchPokemon(): Flow<PagingData<Pokemon>>

    /**
     * Returns a PagingData flow of Pokemon with colors
     */
    fun fetchPokemonWithColors(): Flow<PagingData<PokemonWithColors>>
}