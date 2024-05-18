package cz.hrabe.pokedex.di

import cz.hrabe.pokedex.data.PokemonRepository
import cz.hrabe.pokedex.domain.GetPokemonPagingDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPokemonPagingDataUseCase(pokemonRepository: PokemonRepository) = GetPokemonPagingDataUseCase(pokemonRepository)
}