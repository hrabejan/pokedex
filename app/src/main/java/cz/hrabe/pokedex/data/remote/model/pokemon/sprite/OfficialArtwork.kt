package cz.hrabe.pokedex.data.remote.model.pokemon.sprite

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OfficialArtwork(
    val front_default: String
)
