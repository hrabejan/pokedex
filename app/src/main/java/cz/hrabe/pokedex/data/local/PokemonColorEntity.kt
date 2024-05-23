package cz.hrabe.pokedex.data.local

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.hrabe.pokedex.domain.model.PokemonColors

@Entity("pokemon_color")
data class PokemonColorEntity(
    @PrimaryKey
    @ColumnInfo("pokemon_id")
    val pokemonId: Int,
    @ColumnInfo("average_color")
    val averageColor: Int,
    @ColumnInfo("contrast_color")
    val contrastColor: Int
)

fun PokemonColorEntity.toPokemonColors(): PokemonColors {
    return PokemonColors(
        Color(this.averageColor),
        Color(this.contrastColor)
    )
}