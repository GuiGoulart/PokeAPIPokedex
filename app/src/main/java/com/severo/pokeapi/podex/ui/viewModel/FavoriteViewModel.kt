package com.severo.pokeapi.podex.ui.viewModel

import androidx.lifecycle.MutableLiveData
import com.severo.pokeapi.podex.data.base.BaseViewModel
import com.severo.pokeapi.podex.data.repository.FavoriteRepository
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FavoriteViewModel(
    var favoriteRepository: FavoriteRepository,
    val coroutinesDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val pokemonFavoriteResultLiveData = MutableLiveData<SingleLiveEvent<Resource<List<PokemonResultResponse>>>>()
    val navigateToDetails = MutableLiveData<SingleLiveEvent<Triple<PokemonResultResponse, Int, String?>>>()
    val removePokemonFavorite = MutableLiveData<SingleLiveEvent<Resource<Int>>>()

    fun setupInit(){
        getPokemonFavorite()
    }

    fun onItemDetailClick(
        pokemonResultResponse: PokemonResultResponse,
        dominantColor: Int,
        picture: String?
    ) {
        navigateToDetails.postValue(
            SingleLiveEvent(
                Triple(
                    pokemonResultResponse,
                    dominantColor,
                    picture
                )
            )
        )
    }

    fun onItemRemoveClick(
        pokemonResultResponse: PokemonResultResponse,
        positionFavorite: Int
    ) {
        removePokemonFavorite(pokemonResultResponse, positionFavorite)
    }

    private fun removePokemonFavorite(pokemonResultResponse: PokemonResultResponse, positionFavorite: Int) {
        viewModelScope.launch(coroutinesDispatcher) {
            removePokemonFavorite.postValue(SingleLiveEvent(Resource.loading()))
            try {
                favoriteRepository.deleteFavorite(pokemonResultResponse).apply {
                    removePokemonFavorite.postValue(SingleLiveEvent(Resource.success(positionFavorite)))
                }
            }catch (e: Exception){
                removePokemonFavorite.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }

    private fun getPokemonFavorite() {
        viewModelScope.launch(coroutinesDispatcher) {
            pokemonFavoriteResultLiveData.postValue(SingleLiveEvent(Resource.loading()))
            try {
                val response = favoriteRepository.favoriteAll()
                pokemonFavoriteResultLiveData.postValue(SingleLiveEvent(Resource.success(response)))
            }catch (e: Exception){
                pokemonFavoriteResultLiveData.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }
}