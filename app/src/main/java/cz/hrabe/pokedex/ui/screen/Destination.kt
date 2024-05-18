package cz.hrabe.pokedex.ui.screen

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object List : Destination()
    @Serializable
    data class Detail(val id: Int) : Destination()
}
