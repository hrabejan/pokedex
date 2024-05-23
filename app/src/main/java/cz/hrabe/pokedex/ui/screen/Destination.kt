package cz.hrabe.pokedex.ui.screen

import kotlinx.serialization.Serializable

/**
 * Main destinations of Pokedex application
 */
sealed class Destination {
    /**
     * Meant for displaying a list of all Pokemon
     */
    @Serializable
    data object List : Destination()

    /**
     * Meant for displaying single Pokemon's details
     * @param id pokemon's id
     */
    @Serializable
    data class Detail(val id: Int) : Destination()
}
