package cz.hrabe.pokedex.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import cz.hrabe.pokedex.data.local.PokemonDatabase
import cz.hrabe.pokedex.data.local.PokemonEntity
import cz.hrabe.pokedex.data.local.PokemonWithColorsEntity
import cz.hrabe.pokedex.data.remote.model.pokemon.toPokemonEntity

@OptIn(ExperimentalPagingApi::class)
class PokemonWithColorsRemoteMediator(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase
) : RemoteMediator<Int, PokemonWithColorsEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonWithColorsEntity>
    ): MediatorResult {
        return try {
            val pageSize = state.config.pageSize

            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.pokemonEntity?.id ?: 0
                }
            }

            val pokemonListing =
                pokemonApi.fetchPokemonListing(limit = pageSize, offset = offset)

            val pokemonEntities = pokemonListing.results.map {
                val pokemonDto = pokemonApi.fetchPokemonByName(it.name)
                pokemonDto.toPokemonEntity()
            }

            pokemonDatabase.runInTransaction {
                val pokemonDao = pokemonDatabase.pokemonDao
                if (loadType == LoadType.REFRESH) {
                    pokemonDao.deleteAll()
                }

                pokemonDao.upsertAll(pokemonEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = pokemonListing.next == null
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }
}