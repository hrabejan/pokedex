package cz.hrabe.pokedex.data.remote.model.pokemon.type

import com.squareup.moshi.JsonClass

/**
 * Pokemon's type container class
 */
@JsonClass(generateAdapter = true)
data class TypeNameDto(
    /**
     * Name of the type of Pokemon
     */
    val name: String
)
