package cz.hrabe.pokedex.data

import androidx.paging.PagingData
import cz.hrabe.pokedex.domain.model.Pokemon
import cz.hrabe.pokedex.domain.model.PokemonWithColors
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun fetchPokemon(): Flow<PagingData<Pokemon>>
    fun fetchPokemonWithColors(): Flow<PagingData<PokemonWithColors>>
}