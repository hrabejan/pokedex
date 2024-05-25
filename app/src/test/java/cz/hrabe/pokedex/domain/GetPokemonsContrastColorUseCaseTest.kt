package cz.hrabe.pokedex.domain

import androidx.compose.ui.graphics.Color
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

class GetPokemonsContrastColorUseCaseTest {
    private lateinit var getPokemonsContrastColorUseCase: GetPokemonsContrastColorUseCase

    @Before
    fun setUp() {
        getPokemonsContrastColorUseCase = GetPokemonsContrastColorUseCase()
    }

    @Test
    fun shouldReturnLightColorIfProvidedBlackColor() {

    }
}