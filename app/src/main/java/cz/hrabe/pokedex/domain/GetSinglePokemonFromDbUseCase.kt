package cz.hrabe.pokedex.domain

import cz.hrabe.pokedex.data.local.PokemonDao
import cz.hrabe.pokedex.data.local.toPokemon
import cz.hrabe.pokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSinglePokemonFromDbUseCase @Inject constructor(private val pokemonDao: PokemonDao) {

    operator fun invoke(pokemonId: Int): Flow<Pokemon> = pokemonDao.getPokemonById(pokemonId).map { it.toPokemon() }
}