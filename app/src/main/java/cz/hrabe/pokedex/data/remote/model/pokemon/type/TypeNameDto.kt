package cz.hrabe.pokedex.data.remote.model.pokemon.type

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TypeNameDto(val name: String)
