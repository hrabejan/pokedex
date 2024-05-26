package cz.hrabe.pokedex.domain

import cz.hrabe.pokedex.data.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemonWithColorsPagingDataUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    /**
     * Returns paginated data flow of Pokemon with colors
     */
    operator fun invoke() = pokemonRepository.fetchPokemonWithColors().flowOn(defaultDispatcher)
}