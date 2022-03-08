package com.severo.pokeapi.podex.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.severo.pokeapi.podex.data.base.BaseViewModel
import com.severo.pokeapi.podex.data.repository.PokeApiRepository
import com.severo.pokeapi.podex.data.model.PokemonResultModel
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private var pokemonRepositoryPoke: PokeApiRepository,
    private val coroutinesDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val pokemonResultLiveData = MutableLiveData<SingleLiveEvent<Resource<PagingData<PokemonResultModel>>>>()
    val navigateToDetails = MutableLiveData<SingleLiveEvent<Triple<PokemonResultModel, Int, String?>>>()

    fun setup() {
        getPokemons(null)
    }

    fun onAfterTextChanged(searchString: String?) {
        getPokemons(searchString)
    }

    fun onItemDetailClick(
        pokemonResultModel: PokemonResultModel,
        dominantColor: Int,
        picture: String?
    ) {
        navigateToDetails.postValue(
            SingleLiveEvent(
                Triple(
                    pokemonResultModel,
                    dominantColor,
                    picture
                )
            )
        )
    }

    private fun getPokemons(searchString: String?) {
        pokemonResultLiveData.postValue(SingleLiveEvent(Resource.loading()))

        viewModelScope.launch(coroutinesDispatcher) {
            try {
                val response = pokemonRepositoryPoke.getPokemon(searchString)
                response.flow.collectLatest {
                    pokemonResultLiveData.postValue(SingleLiveEvent(Resource.success(it)))
                }
            } catch (e: Exception) {
                pokemonResultLiveData.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }
}