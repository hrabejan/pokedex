package cz.hrabe.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.hrabe.pokedex.data.local.converters.TagsConverter

@TypeConverters(TagsConverter::class)
@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao

    companion object {
        const val NAME = "pokemon_db"
    }
}