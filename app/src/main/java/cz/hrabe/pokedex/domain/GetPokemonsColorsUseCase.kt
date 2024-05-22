package cz.hrabe.pokedex.domain

import cz.hrabe.pokedex.data.local.PokemonColorDao
import cz.hrabe.pokedex.data.local.PokemonColorEntity
import kotlinx.coroutines.flow.Flow

class GetPokemonsColorsUseCase(private val pokemonColorDao: PokemonColorDao) {

    operator fun invoke(pokemonId: Int) : Flow<PokemonColorEntity> = pokemonColorDao.getPokemonColor(pokemonId)
}