package cz.hrabe.pokedex.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import cz.hrabe.pokedex.data.local.PokemonDatabase
import cz.hrabe.pokedex.data.local.model.PokemonEntity
import cz.hrabe.pokedex.data.remote.model.pokemon.toPokemonEntity

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase
) : RemoteMediator<Int, PokemonEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
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
                    lastItem?.id ?: 0
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
}