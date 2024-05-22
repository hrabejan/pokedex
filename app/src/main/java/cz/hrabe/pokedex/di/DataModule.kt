package cz.hrabe.pokedex.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import cz.hrabe.pokedex.data.PokemonRepository
import cz.hrabe.pokedex.data.PokemonRepositoryImpl
import cz.hrabe.pokedex.data.local.MIGRATION_1_2
import cz.hrabe.pokedex.data.local.MIGRATION_2_3
import cz.hrabe.pokedex.data.local.PokemonDao
import cz.hrabe.pokedex.data.local.PokemonDatabase
import cz.hrabe.pokedex.data.local.PokemonEntity
import cz.hrabe.pokedex.data.remote.PokemonApi
import cz.hrabe.pokedex.data.remote.PokemonRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return Room.databaseBuilder(context, PokemonDatabase::class.java, PokemonDatabase.NAME)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    @Provides
    fun providePokemonDao(pokemonDatabase: PokemonDatabase): PokemonDao = pokemonDatabase.pokemonDao

    @Provides
    @Singleton
    fun providePokemonPager(
        pokemonDatabase: PokemonDatabase,
        pokemonApi: PokemonApi
    ): Pager<Int, PokemonEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pokemonDatabase.pokemonDao.pagingSource() },
            remoteMediator = PokemonRemoteMediator(pokemonApi, pokemonDatabase)
        )
    }

    @Provides
    @Singleton
    fun providePokemonRepository(pager: Pager<Int, PokemonEntity>): PokemonRepository {
        return PokemonRepositoryImpl(pager)
    }

}