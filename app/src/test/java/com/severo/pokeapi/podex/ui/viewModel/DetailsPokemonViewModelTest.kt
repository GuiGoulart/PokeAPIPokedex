package com.severo.pokeapi.podex.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.severo.pokeapi.podex.data.repository.BeeceptorRepository
import com.severo.pokeapi.podex.data.repository.PokeApiRepository
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.model.SinglePokemonResponse
import com.severo.pokeapi.podex.util.CoroutinesTestRule
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
class DetailsPokemonViewModelTest {

    @get:Rule
    val instantRuleLiveData = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: DetailsPokemonViewModel
    private val observerPokemonDetailResultLiveData: Observer<SingleLiveEvent<Resource<SinglePokemonResponse>>> = mockk(relaxed = true)
    private val observerBeeceptorResultLiveData: Observer<SingleLiveEvent<Resource<Unit>>> = mockk(relaxed = true)
    private val observerOnClickPokemonDetailLiveData: Observer<SingleLiveEvent<Boolean>> = mockk(relaxed = true)
    private val pokemonApiRepository: PokeApiRepository = mockk(relaxed = true)
    private val beeceptorRepository: BeeceptorRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = DetailsPokemonViewModel(pokemonApiRepository, beeceptorRepository, coroutinesTestRule.testDispatcher)
        prepareObserver()
    }

    @Test
    fun `when onClickFavoritePokemon is call then request succeeds`() = runTest {
        val mockkSinglePokemonResponse = mockkClass(SinglePokemonResponse::class)
        viewModel.onClickFavoritePokemon(mockkSinglePokemonResponse)

        verify {
            observerOnClickPokemonDetailLiveData.onChanged(
                SingleLiveEvent(
                    true
                )
            )
        }
    }

    @Test
    fun `when getPokemonDetail is call then request fails`() = runBlocking {
        coEvery {
            pokemonApiRepository.getSinglePokemon(1)
        } throws SocketTimeoutException()

        viewModel.getPokemonDetail("https://pokeapi.co/api/v2/pokemon/1")

        coVerifyOrder {
            observerPokemonDetailResultLiveData.onChanged(SingleLiveEvent(Resource.loading()))
            observerPokemonDetailResultLiveData.onChanged(SingleLiveEvent(Resource.error()))
        }
    }

    @Test
    fun `when getPokemonDetail is call then request success`() = runBlocking {
        val mockkSinglePokemonResponse = mockk<SinglePokemonResponse>(relaxed = true)
        coEvery {
            pokemonApiRepository.getSinglePokemon(1)
        } returns mockkSinglePokemonResponse

        viewModel.getPokemonDetail("https://pokeapi.co/api/v2/pokemon/1")

        coVerifyOrder {
            observerPokemonDetailResultLiveData.onChanged(SingleLiveEvent(Resource.loading()))
            observerPokemonDetailResultLiveData.onChanged(SingleLiveEvent(Resource.success(mockkSinglePokemonResponse)))
        }
    }

    @Test
    fun `when postBeeceptor is call then request success`() = runBlocking {
        val mockkSinglePokemonResponse = mockk<SinglePokemonResponse>(relaxed = true)
        coEvery {
            beeceptorRepository.postWebhookBeeceptor(mockkSinglePokemonResponse)
        } just runs

        viewModel.postBeeceptor(mockkSinglePokemonResponse)

        coVerifyOrder {
            observerBeeceptorResultLiveData.onChanged(SingleLiveEvent(Resource.loading()))
            observerBeeceptorResultLiveData.onChanged(SingleLiveEvent(Resource.success(Unit)))
        }
    }

    private fun prepareObserver() {
        viewModel.pokemonDetailResultLiveData.observeForever(observerPokemonDetailResultLiveData)
        viewModel.onClickPokemonDetailLiveData.observeForever(observerOnClickPokemonDetailLiveData)
        viewModel.beeceptorResultLiveData.observeForever(observerBeeceptorResultLiveData)
    }

    private companion object {
        const val SEARCH = "bulbasauro"
    }
}