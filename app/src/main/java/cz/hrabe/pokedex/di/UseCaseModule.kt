package cz.hrabe.pokedex.di

import cz.hrabe.pokedex.data.PokemonRepository
import cz.hrabe.pokedex.data.local.PokemonDao
import cz.hrabe.pokedex.domain.GetPokemonPagingDataUseCase
import cz.hrabe.pokedex.domain.GetPokemonsContrastColorUseCase
import cz.hrabe.pokedex.domain.GetSinglePokemonFromDbUseCase
import cz.hrabe.pokedex.domain.UpdatePokemonAverageColorUserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPokemonPagingDataUseCase(pokemonRepository: PokemonRepository) =
        GetPokemonPagingDataUseCase(pokemonRepository)

    @Provides
    fun provideGetSinglePokemonFromDbUseCase(pokemonDao: PokemonDao) =
        GetSinglePokemonFromDbUseCase(pokemonDao)

    @Provides
    fun provideUpdatePokemonAverageColorUseCase(
        pokemonDao: PokemonDao,
        getPokemonsContrastColorUseCase: GetPokemonsContrastColorUseCase
    ) =
        UpdatePokemonAverageColorUserCase(pokemonDao, getPokemonsContrastColorUseCase)

    @Provides
    fun provideGetPokemonsContrastColorUseCase() = GetPokemonsContrastColorUseCase()
}