package com.severo.pokeapi.podex.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.severo.pokeapi.podex.data.repository.PokeApiRepository
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.util.CoroutinesTestRule
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantRuleLiveData = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: HomeViewModel
    private val observerPokemonResult: Observer<SingleLiveEvent<Resource<PagingData<PokemonResultResponse>>>> =
        mockk(relaxed = true)
    private val observerNavigateToDetails: Observer<SingleLiveEvent<Triple<PokemonResultResponse, Int, String?>>> =
        mockk(relaxed = true)
    private val pokemonApiRepository: PokeApiRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = HomeViewModel(pokemonApiRepository, coroutinesTestRule.testDispatcher)
        prepareObserver()
    }

    @Test
    fun `when onAfterTextChanged is call then request fails`() = runTest {
        coEvery {
            pokemonApiRepository.getPokemon(SEARCH)
        } throws SocketTimeoutException()

        viewModel.onAfterTextChanged(SEARCH)

        coVerifyOrder {
            observerPokemonResult.onChanged(SingleLiveEvent(Resource.loading()))
            observerPokemonResult.onChanged(SingleLiveEvent(Resource.error()))
        }
    }

    @Test
    fun `when onAfterTextChanged is call then request succeeds`() = runBlocking {
        val pagingDataResultMock = mockk<PagingData<PokemonResultResponse>>(relaxed = true)
        val flowPagingDataMock = flowOf(pagingDataResultMock)
        coEvery {
            pokemonApiRepository.getPokemon(SEARCH)
        } returns mockk {
            every {
                flow
            } returns flowPagingDataMock
        }

        viewModel.onAfterTextChanged(SEARCH)

        coVerifyOrder {
            observerPokemonResult.onChanged(SingleLiveEvent(Resource.loading()))
            observerPokemonResult.onChanged(SingleLiveEvent(Resource.success(pagingDataResultMock)))
        }
    }

    @Test
    fun `when onItemDetailClick is call then request succeeds`() {
        val pokemonResultResponse = mockkClass(PokemonResultResponse::class)
        viewModel.onItemDetailClick(pokemonResultResponse, 1, null)

        verify {
            observerNavigateToDetails.onChanged(
                SingleLiveEvent(
                    Triple(
                        pokemonResultResponse,
                        1,
                        null
                    )
                )
            )
        }
    }

    private fun prepareObserver() {
        viewModel.pokemonResultLiveData.observeForever(observerPokemonResult)
        viewModel.navigateToDetails.observeForever(observerNavigateToDetails)
    }

    private companion object {
        const val SEARCH = "bulbasauro"
    }
}