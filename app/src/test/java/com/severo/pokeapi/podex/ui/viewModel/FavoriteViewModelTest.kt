package com.severo.pokeapi.podex.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.severo.pokeapi.podex.data.repository.FavoriteRepository
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.util.CoroutinesTestRule
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    @get:Rule
    val instantRuleLiveData = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: FavoriteViewModel
    private val observerPokemonFavoriteResultLiveData: Observer<SingleLiveEvent<Resource<List<PokemonResultResponse>>>> = mockk(relaxed = true)
    private val observerVavigateToDetails: Observer<SingleLiveEvent<Triple<PokemonResultResponse, Int, String?>>> = mockk(relaxed = true)
    private val observerRemovePokemonFavorite: Observer<SingleLiveEvent<Resource<Int>>> = mockk(relaxed = true)
    private val favoriteRepository: FavoriteRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = FavoriteViewModel(favoriteRepository, coroutinesTestRule.testDispatcher)
        prepareObserver()
    }

    @Test
    fun `when setupInit is call then request fails`() = runBlocking {
        coEvery {
            favoriteRepository.favoriteAll()
        } throws SocketTimeoutException()

        viewModel.setupInit()

        coVerifyOrder {
            observerPokemonFavoriteResultLiveData.onChanged(SingleLiveEvent(Resource.loading()))
            observerPokemonFavoriteResultLiveData.onChanged(SingleLiveEvent(Resource.error()))
        }
    }

    @Test
    fun `when setupInit is call then request success`() = runBlocking {
        val mockkPokemonResultResponse = mockk<List<PokemonResultResponse>>(relaxed = true)
        coEvery {
            favoriteRepository.favoriteAll()
        } returns mockkPokemonResultResponse

        viewModel.setupInit()

        coVerifyOrder {
            observerPokemonFavoriteResultLiveData.onChanged(SingleLiveEvent(Resource.loading()))
            observerPokemonFavoriteResultLiveData.onChanged(SingleLiveEvent(Resource.success(mockkPokemonResultResponse)))
        }
    }

    @Test
    fun `when onItemDetailClick is call then navigation`() = runBlocking {
        val mockkPokemonResultResponse = mockk<PokemonResultResponse>(relaxed = true)
        val mockkDominantColor = DOMINANT_COLOR
        val mockkPicture = PICTURE
        viewModel.onItemDetailClick(mockkPokemonResultResponse, mockkDominantColor, mockkPicture)

        coVerifyOrder {
            observerVavigateToDetails.onChanged(SingleLiveEvent(Triple(
                mockkPokemonResultResponse,
                mockkDominantColor,
                mockkPicture
            )))
        }
    }

    @Test
    fun `when onItemRemoveClick is call then request fails`() = runBlocking {
        val mockkPokemonResultResponse = mockk<PokemonResultResponse>(relaxed = true)
        val mockkPositionFavorite = POSITION_FAVORITE

        coEvery {
            favoriteRepository.deleteFavorite(mockkPokemonResultResponse)
        } throws SocketTimeoutException()

        viewModel.onItemRemoveClick(mockkPokemonResultResponse, mockkPositionFavorite)

        coVerifyOrder {
            observerRemovePokemonFavorite.onChanged(SingleLiveEvent(Resource.loading()))
            observerRemovePokemonFavorite.onChanged(SingleLiveEvent(Resource.error()))
        }
    }

    @Test
    fun `when onItemRemoveClick is call then request success`() = runBlocking {
        val mockkPokemonResultResponse = mockk<PokemonResultResponse>(relaxed = true)
        val mockkPositionFavorite = POSITION_FAVORITE

        coEvery {
            favoriteRepository.deleteFavorite(mockkPokemonResultResponse)
        } just runs

        viewModel.onItemRemoveClick(mockkPokemonResultResponse, mockkPositionFavorite)

        coVerifyOrder {
            observerRemovePokemonFavorite.onChanged(SingleLiveEvent(Resource.loading()))
            observerRemovePokemonFavorite.onChanged(SingleLiveEvent(Resource.success(mockkPositionFavorite)))
        }
    }

    private fun prepareObserver() {
        viewModel.pokemonFavoriteResultLiveData.observeForever(observerPokemonFavoriteResultLiveData)
        viewModel.navigateToDetails.observeForever(observerVavigateToDetails)
        viewModel.removePokemonFavorite.observeForever(observerRemovePokemonFavorite)
    }

    private companion object {
        const val PICTURE = "urlPicture"
        const val DOMINANT_COLOR = 73893
        const val POSITION_FAVORITE = 73893
    }
}