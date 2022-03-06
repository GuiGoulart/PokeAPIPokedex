package com.severo.pokeapi.podex.repository

import com.severo.pokeapi.podex.data.repository.BeeceptorRepository
import com.severo.pokeapi.podex.data.service.BeeceptorApi
import com.severo.pokeapi.podex.model.SinglePokemonResponse
import com.severo.pokeapi.podex.util.CoroutinesTestRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class BeeceptorRepositoryTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var repository: BeeceptorRepository
    private val beeceptorApi: BeeceptorApi = mockk(relaxed = true)

    @Before
    fun setup() {
        repository = BeeceptorRepository(beeceptorApi)
    }

    @Test
    fun `when repository is call main request is successful`() = runBlocking {
        coEvery {
            beeceptorApi.postWebhookBeeceptor(mockSinglePokemonResponse())
        } just Runs

        val result = repository.postWebhookBeeceptor(mockSinglePokemonResponse())

        assertEquals(result, Unit)
    }

    private fun mockSinglePokemonResponse() = mockk<SinglePokemonResponse>(relaxed = true)

}