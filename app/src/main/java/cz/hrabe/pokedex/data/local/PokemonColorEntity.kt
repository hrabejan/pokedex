package cz.hrabe.pokedex.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("pokemon_color")
data class PokemonColorEntity(
    @PrimaryKey
    @ColumnInfo("pokemon_id")
    val pokemonId: Int,
    @ColumnInfo("average_color")
    val averageColor: String,
    @ColumnInfo("contrast_color")
    val contrastColor: String
)