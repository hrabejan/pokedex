package cz.hrabe.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.hrabe.pokedex.data.local.converters.TagsConverter

@TypeConverters(TagsConverter::class)
@Database(entities = [PokemonEntity::class], version = 2)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao

    companion object {
        const val NAME = "pokemon_db"
    }
}
val MIGRATION_1_2 = object : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE pokemon ADD COLUMN average_color TEXT")
    }

}