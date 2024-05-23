package cz.hrabe.pokedex.data.local

import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.hrabe.pokedex.data.local.converters.TagsConverter

@TypeConverters(TagsConverter::class)
@Database(
    entities = [PokemonEntity::class, PokemonColorEntity::class], version = 4)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val pokemonColorsDao: PokemonColorDao

    companion object {
        const val NAME = "pokemon_db"
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE pokemon ADD COLUMN average_color TEXT")
    }

}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE pokemon ADD COLUMN base_experience INTEGER NOT NULL DEFAULT 0")
    }
}