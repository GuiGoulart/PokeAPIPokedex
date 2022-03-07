package com.severo.pokeapi.podex.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.severo.pokeapi.podex.data.repository.BeeceptorRepository
import com.severo.pokeapi.podex.data.repository.FavoriteRepository
import com.severo.pokeapi.podex.data.repository.PokeApiRepository
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.model.SinglePokemonResponse
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
class DetailsPokemonViewModelTest {

    @get:Rule
    val instantRuleLiveData = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: DetailsPokemonViewModel

    private val observerPokemonDetailResultLiveData: Observer<SingleLiveEvent<Resource<SinglePokemonResponse>>> = mockk(relaxed = true)
    private val observerBeeceptorResultLiveData: Observer<SingleLiveEvent<Resource<Unit>>> = mockk(relaxed = true)
    private val observerInsertPokemonFavorite: Observer<SingleLiveEvent<Resource<Unit>>> = mockk(relaxed = true)
    private val observerRemovePokemonFavorite: Observer<SingleLiveEvent<Resource<Unit>>> = mockk(relaxed = true)
    private val observerPokemonFavoriteResultLiveData: Observer<SingleLiveEvent<Resource<List<PokemonResultResponse>>>> = mockk(relaxed = true)

    private val pokemonApiRepository: PokeApiRepository = mockk(relaxed = true)
    private val beeceptorRepository: BeeceptorRepository = mockk(relaxed = true)
    private val favoriteRepository: FavoriteRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = DetailsPokemonViewModel(pokemonApiRepository, beeceptorRepository, favoriteRepository, coroutinesTestRule.testDispatcher)
        prepareObserver()
    }

    @Test
    fun `when getPokemonDetail is call then request fails`() = runBlocking {
        coEvery {
            pokemonApiRepository.getSinglePokemon(1)
        } throws SocketTimeoutException()

        viewModel.getPokemonDetail(URL)

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

        viewModel.getPokemonDetail(URL)

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

    @Test
    fun `when onClickAddFavoritePokemon is call then request success`() = runBlocking {
        val mockkSinglePokemonResponse = mockk<SinglePokemonResponse>(relaxed = true)
        val mockkPokemonResultResponse = mockk<PokemonResultResponse>(relaxed = true)
        coEvery {
            favoriteRepository.insertFavorite(mockkPokemonResultResponse)
        } just runs

        viewModel.onClickAddFavoritePokemon(mockkSinglePokemonResponse, mockkPokemonResultResponse)

        coVerifyOrder {
            observerInsertPokemonFavorite.onChanged(SingleLiveEvent(Resource.loading()))
            observerInsertPokemonFavorite.onChanged(SingleLiveEvent(Resource.success(Unit)))
        }
    }

    @Test
    fun `when onClickAddFavoritePokemon is call then request fails`() = runBlocking {
        val mockkSinglePokemonResponse = mockk<SinglePokemonResponse>(relaxed = true)
        val mockkPokemonResultResponse = mockk<PokemonResultResponse>(relaxed = true)
        coEvery {
            favoriteRepository.insertFavorite(mockkPokemonResultResponse)
        } throws SocketTimeoutException()

        viewModel.onClickAddFavoritePokemon(mockkSinglePokemonResponse, mockkPokemonResultResponse)

        coVerifyOrder {
            observerInsertPokemonFavorite.onChanged(SingleLiveEvent(Resource.loading()))
            observerInsertPokemonFavorite.onChanged(SingleLiveEvent(Resource.error()))
        }
    }

    @Test
    fun `when onClickRemoveFavoritePokemon is call then request success`() = runBlocking {
        val mockkSinglePokemonResponse = mockk<SinglePokemonResponse>(relaxed = true)
        val mockkPokemonResultResponse = mockk<PokemonResultResponse>(relaxed = true)
        coEvery {
            favoriteRepository.deleteFavorite(mockkPokemonResultResponse)
        } just runs

        viewModel.onClickRemoveFavoritePokemon(mockkSinglePokemonResponse, mockkPokemonResultResponse)

        coVerifyOrder {
            observerRemovePokemonFavorite.onChanged(SingleLiveEvent(Resource.loading()))
            observerRemovePokemonFavorite.onChanged(SingleLiveEvent(Resource.success(Unit)))
        }
    }

    @Test
    fun `when setupInit is call then request success`() = runBlocking {
        val mockkListPokemonResultResponse = mockk<List<PokemonResultResponse>>(relaxed = true)
        coEvery {
            favoriteRepository.favoriteAll()
        } returns mockkListPokemonResultResponse

        viewModel.setupInit(URL)

        coVerifyOrder {
            observerPokemonFavoriteResultLiveData.onChanged(SingleLiveEvent(Resource.success(mockkListPokemonResultResponse)))
        }
    }

    @Test
    fun `when getPokemonFavorite is call then request success`() = runBlocking {
        val mockkSinglePokemonResponse = mockk<SinglePokemonResponse>(relaxed = true)
        val mockkPokemonResultResponse = mockk<PokemonResultResponse>(relaxed = true)
        coEvery {
            favoriteRepository.deleteFavorite(mockkPokemonResultResponse)
        } throws SocketTimeoutException()

        viewModel.onClickRemoveFavoritePokemon(mockkSinglePokemonResponse, mockkPokemonResultResponse)

        coVerifyOrder {
            observerRemovePokemonFavorite.onChanged(SingleLiveEvent(Resource.loading()))
            observerRemovePokemonFavorite.onChanged(SingleLiveEvent(Resource.error()))
        }
    }

    private fun prepareObserver() {
        viewModel.pokemonDetailResultLiveData.observeForever(observerPokemonDetailResultLiveData)
        viewModel.beeceptorResultLiveData.observeForever(observerBeeceptorResultLiveData)
        viewModel.insertPokemonFavorite.observeForever(observerInsertPokemonFavorite)
        viewModel.removePokemonFavorite.observeForever(observerRemovePokemonFavorite)
        viewModel.pokemonFavoriteResultLiveData.observeForever(observerPokemonFavoriteResultLiveData)
    }

    private companion object {
        const val URL = "https://pokeapi.co/api/v2/pokemon/1"
    }
}