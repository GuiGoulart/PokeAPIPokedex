package com.severo.pokeapi.podex.repository

import com.severo.pokeapi.podex.data.repository.PokeApiRepository
import com.severo.pokeapi.podex.data.service.PokemonApi
import com.severo.pokeapi.podex.model.PokemonResponse
import com.severo.pokeapi.podex.model.SinglePokemonResponse
import com.severo.pokeapi.podex.util.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class PokeApiRepositoryTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var repository: PokeApiRepository
    private val pokemonApi: PokemonApi = mockk(relaxed = true)

    @Before
    fun setup() {
        repository = PokeApiRepository(pokemonApi)
    }

    @Test
    fun `when repository getSinglePokemon is call main request is successful`() = runBlocking {
        val mockk = mockk<Response<SinglePokemonResponse>>(relaxed = true)
        coEvery {
            pokemonApi.getSinglePokemon(ID)
        } returns mockk

        val result = repository.getSinglePokemon(ID)

        assertEquals(result, mockk)
    }

    private companion object {
        const val SEARCH = "bulbasauro"
        const val ID = 1
    }

}