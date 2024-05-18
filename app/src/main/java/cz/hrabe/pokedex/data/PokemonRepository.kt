package cz.hrabe.pokedex.data

import androidx.paging.PagingData
import cz.hrabe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun fetchPokemon(): Flow<PagingData<Pokemon>>
}