package cz.hrabe.pokedex.domain.model

import org.junit.Assert.*

import org.junit.Test

class PokemonTest {
    private val pokemon = Pokemon(
        id = 1,
        name = "Test Pokemon",
        "",
        10,
        250,
        emptyList(),
        10
    )

    @Test
    fun leftoverInches_containsOneDecimal_ifOneDecimalSpecified() {
        val stringInches = pokemon.leftoverInches(1)

        //Has a decimal point
        assertTrue(stringInches.contains('.'))

        //Get the decimal part of string
        val decimalPart = stringInches.split('.')[1]

        //Assert decimals size
        val expectedSize = 1
        assertEquals(expectedSize, decimalPart.length)
    }

    @Test
    fun leftOverInches_fallbacksToNoDecimals_ifProvidedNegative() {
        val stringInches = pokemon.leftoverInches(-1)

        //Does not have a decimal point
        assertTrue(!stringInches.contains('.'))
    }

    @Test
    fun leftOverInches_hasNoDecimalPoint_ifZeroSpecified() {
        val stringInches = pokemon.leftoverInches(0)

        //Does not have a decimal point
        assertTrue(!stringInches.contains('.'))
    }
}