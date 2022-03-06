package com.severo.pokeapi.podex.repository

import androidx.paging.Pager
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
        val mockkSinglePokemonResponse = mockk<SinglePokemonResponse>(relaxed = true)
        coEvery {
            pokemonApi.getSinglePokemon(ID)
        } returns mockkSinglePokemonResponse

        val result = repository.getSinglePokemon(ID)

        assertEquals(result, mockkSinglePokemonResponse)
    }

    @Test
    fun `when repository getPokemon is call main request is successful`() = runBlocking {
        val mockkPokemonResponse = mockk<PokemonResponse>(relaxed = true)
        val mockkPager = mockk<Pager<Int, PokemonResponse>>(relaxed = true)
        coEvery {
            pokemonApi.getPokemons(100, null)
        } returns mockkPokemonResponse

        val result = repository.getPokemon(SEARCH)

        assertEquals(result, mockkPokemonResponse)
    }

    private companion object {
        const val SEARCH = "bulbasauro"
        const val ID = 1
    }

}