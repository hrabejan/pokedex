package cz.hrabe.pokedex.data.remote.model.pokemon.sprite

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OtherSprites(
    @Json(name = "official-artwork")
    val officialArtwork: OfficialArtwork
)
