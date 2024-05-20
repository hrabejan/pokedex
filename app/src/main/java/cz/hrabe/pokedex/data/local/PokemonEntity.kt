package cz.hrabe.pokedex.data.local

import androidx.core.graphics.toColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.hrabe.pokedex.domain.Pokemon

@Entity("pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val tags: List<String>,
    @ColumnInfo(name = "average_color")
    val averageColor: String?
)

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        this.id,
        this.name,
        this.imageUrl,
        this.height,
        this.weight,
        this.tags,
        this.averageColor?.let {
            "#$it".toColorInt()
        }
    )
}
