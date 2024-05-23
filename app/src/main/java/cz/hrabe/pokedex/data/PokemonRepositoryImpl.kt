package cz.hrabe.pokedex.data

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import cz.hrabe.pokedex.data.local.model.PokemonEntity
import cz.hrabe.pokedex.data.local.model.PokemonWithColorsEntity
import cz.hrabe.pokedex.data.local.model.toPokemon
import cz.hrabe.pokedex.data.local.model.toPokemonWithColors
import cz.hrabe.pokedex.domain.model.Pokemon
import cz.hrabe.pokedex.domain.model.PokemonWithColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonPager: Pager<Int, PokemonEntity>,
    private val pokemonColorPager: Pager<Int, PokemonWithColorsEntity>
) : PokemonRepository {
    override fun fetchPokemon(): Flow<PagingData<Pokemon>> {
        return pokemonPager.flow.map { pagingData ->
            pagingData.map {
                it.toPokemon()
            }
        }
    }

    override fun fetchPokemonWithColors(): Flow<PagingData<PokemonWithColors>> {
        return pokemonColorPager.flow.map { pagingData ->
            pagingData.map {
                it.toPokemonWithColors()
            }
        }.distinctUntilChanged()
    }

}