package cz.hrabe.pokedex.domain

import cz.hrabe.pokedex.data.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemonPagingDataUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    operator fun invoke() = pokemonRepository.fetchPokemon().flowOn(defaultDispatcher)
}