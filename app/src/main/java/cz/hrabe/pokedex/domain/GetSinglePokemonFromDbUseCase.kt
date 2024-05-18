package cz.hrabe.pokedex.domain

import cz.hrabe.pokedex.data.local.PokemonDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSinglePokemonFromDbUseCase @Inject constructor(private val pokemonDao: PokemonDao) {

    operator fun invoke(pokemonId: Int): Flow<Pokemon> = pokemonDao.getPokemonById(pokemonId)
}