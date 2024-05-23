package cz.hrabe.pokedex.domain

import cz.hrabe.pokedex.data.local.PokemonColorDao
import cz.hrabe.pokedex.data.local.model.toPokemonColors
import cz.hrabe.pokedex.domain.model.PokemonColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetPokemonsColorsUseCase(private val pokemonColorDao: PokemonColorDao) {

    operator fun invoke(pokemonId: Int): Flow<PokemonColors> =
        pokemonColorDao.getPokemonColor(pokemonId).filterNotNull().distinctUntilChanged().map {
            it.toPokemonColors()
        }
}