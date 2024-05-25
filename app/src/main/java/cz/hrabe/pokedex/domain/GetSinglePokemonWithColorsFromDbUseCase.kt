package cz.hrabe.pokedex.domain

import cz.hrabe.pokedex.data.local.PokemonDao
import cz.hrabe.pokedex.data.local.model.toPokemonWithColors
import cz.hrabe.pokedex.domain.model.PokemonWithColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSinglePokemonWithColorsFromDbUseCase @Inject constructor(private val pokemonDao: PokemonDao) {

    operator fun invoke(pokemonId: Int): Flow<PokemonWithColors> = pokemonDao.getPokemonWithColor(pokemonId).map { it.toPokemonWithColors() }
}