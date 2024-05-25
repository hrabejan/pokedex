package cz.hrabe.pokedex.domain

import cz.hrabe.pokedex.data.PokemonRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetPokemonWithColorsPagingDataUseCaseTest {
    private lateinit var repository: PokemonRepository
    private lateinit var useCase: GetPokemonWithColorsPagingDataUseCase

    @Before
    fun setUp() {
        repository = mockk<PokemonRepository>()
        useCase = GetPokemonWithColorsPagingDataUseCase(repository)
    }

    @Test
    fun shouldCallFetchPokemonWithColorsOnce() = runTest {
        every { repository.fetchPokemonWithColors() } returns emptyFlow()
        useCase.invoke()

        verify(atMost = 1) { repository.fetchPokemonWithColors() }
    }
}