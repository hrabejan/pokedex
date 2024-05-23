package cz.hrabe.pokedex.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonColorDao {

    @Upsert
    fun upsert(pokemonColorEntity: PokemonColorEntity)

    @Query("DELETE FROM pokemon_color")
    fun deleteAll()

    @Query("SELECT * FROM pokemon_color WHERE pokemon_id=:pokemonId")
    fun getPokemonColor(pokemonId: Int): Flow<PokemonColorEntity?>
}