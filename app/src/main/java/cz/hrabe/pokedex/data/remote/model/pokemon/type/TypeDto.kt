package cz.hrabe.pokedex.data.remote.model.pokemon.type

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TypeDto(val type: TypeNameDto)