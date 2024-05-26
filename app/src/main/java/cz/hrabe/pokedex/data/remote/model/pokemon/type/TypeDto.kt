package cz.hrabe.pokedex.data.remote.model.pokemon.type

import com.squareup.moshi.JsonClass

/**
 * Encapsulating class for Pokemon's type container
 */
@JsonClass(generateAdapter = true)
data class TypeDto(val type: TypeNameDto)