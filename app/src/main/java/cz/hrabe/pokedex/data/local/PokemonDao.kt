package cz.hrabe.pokedex.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import cz.hrabe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon WHERE id=:id")
    fun getPokemonById(id: Int): Flow<PokemonEntity>

    @Upsert
    fun upsertAll(pokemon: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon")
    fun pagingSource(): PagingSource<Int, PokemonEntity>

    @Query("DELETE from pokemon")
    fun deleteAll()

    @Query("UPDATE pokemon SET average_color=:color WHERE id=:id")
    fun updateAvgColor(id: Int, color: String)
}