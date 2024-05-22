package cz.hrabe.pokedex.domain

import cz.hrabe.pokedex.data.local.PokemonColorDao
import cz.hrabe.pokedex.data.local.PokemonColorEntity
import cz.hrabe.pokedex.data.local.toPokemonColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPokemonsColorsUseCase(private val pokemonColorDao: PokemonColorDao) {

    operator fun invoke(pokemonId: Int) : Flow<PokemonColors> = pokemonColorDao.getPokemonColor(pokemonId).map { it.toPokemonColors() }
}