package cz.hrabe.pokedex.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
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

    @Query("SELECT * FROM pokemon LEFT JOIN pokemon_color ON pokemon.id = pokemon_color.pokemon_id")
    fun withColorPagingSource(): PagingSource<Int, PokemonWithColorsEntity>
}