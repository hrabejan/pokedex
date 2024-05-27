package cz.hrabe.pokedex.domain

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import io.mockk.MockK
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

class GetPokemonsContrastColorUseCaseTest {
    private lateinit var getPokemonsContrastColorUseCase: GetPokemonsContrastColorUseCase

    private val darkColor = -1
    private val onDark = 0
    private val onLight = 1

    @Before
    fun setUp() {
        getPokemonsContrastColorUseCase = GetPokemonsContrastColorUseCase()
        mockkStatic(ColorUtils::class)
    }

    @Test
    fun shouldReturnOnDarkColorIfProvidedDarkColor() {
        every { ColorUtils.calculateLuminance(darkColor) } returns 0.1

        val result = getPokemonsContrastColorUseCase(darkColor, onDark, onLight)

        assertEquals(onDark, result)
    }

    @Test
    fun shouldReturnOnLightColorIfProvidedLightColor() {
        every { ColorUtils.calculateLuminance(darkColor) } returns 0.5

        val result = getPokemonsContrastColorUseCase(darkColor, onDark, onLight)

        assertEquals(onLight, result)
    }

    @After
    fun tearDown() {
        unmockkStatic(ColorUtils::class)
    }
}