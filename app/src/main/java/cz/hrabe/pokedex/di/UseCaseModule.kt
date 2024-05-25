package cz.hrabe.pokedex.di

import cz.hrabe.pokedex.data.PokemonRepository
import cz.hrabe.pokedex.data.local.PokemonColorDao
import cz.hrabe.pokedex.data.local.PokemonDao
import cz.hrabe.pokedex.domain.GetPokemonWithColorsPagingDataUseCase
import cz.hrabe.pokedex.domain.GetPokemonsColorsUseCase
import cz.hrabe.pokedex.domain.GetPokemonsContrastColorUseCase
import cz.hrabe.pokedex.domain.GetSinglePokemonWithColorsFromDbUseCase
import cz.hrabe.pokedex.domain.UpdatePokemonsColorsUserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPokemonWithColorsPagingDataUseCase(pokemonRepository: PokemonRepository) =
        GetPokemonWithColorsPagingDataUseCase(pokemonRepository)

    @Provides
    fun provideGetSinglePokemonFromDbUseCase(pokemonDao: PokemonDao) =
        GetSinglePokemonWithColorsFromDbUseCase(pokemonDao)

    @Provides
    fun provideUpdatePokemonsColorsUseCase(
        pokemonColorDao: PokemonColorDao,
        getPokemonsContrastColorUseCase: GetPokemonsContrastColorUseCase
    ) =
        UpdatePokemonsColorsUserCase(pokemonColorDao, getPokemonsContrastColorUseCase)

    @Provides
    fun provideGetPokemonsContrastColorUseCase() = GetPokemonsContrastColorUseCase()

    @Provides
    fun provideGetPokemonsColorsUseCase(pokemonColorDao: PokemonColorDao) =
        GetPokemonsColorsUseCase(pokemonColorDao)
}