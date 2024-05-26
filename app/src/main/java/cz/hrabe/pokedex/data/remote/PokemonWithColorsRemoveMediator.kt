package cz.hrabe.pokedex.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import cz.hrabe.pokedex.data.local.PokemonDatabase
import cz.hrabe.pokedex.data.local.model.PokemonWithColorsEntity
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
            //Get specified size of pages
            val pageSize = state.config.pageSize

            val offset = when (loadType) {
                //List is being refreshed, load from the beginning -> 0
                LoadType.REFRESH -> 0
                //Since we are listing ony towards the bottom, no prepend loading is used, hence the return Success.
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    //Last available item's id in the list will serve as offset for the next API call
                    val lastItem = state.lastItemOrNull()
                    //If last item not available, we're loading from the start -> return 0
                    lastItem?.pokemonEntity?.id ?: 0
                }
            }

            //Fetch basic pokemon listing
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