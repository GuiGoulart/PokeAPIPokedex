package com.severo.pokeapi.podex.ui.viewModel

import androidx.lifecycle.MutableLiveData
import com.severo.pokeapi.podex.data.base.BaseViewModel
import com.severo.pokeapi.podex.data.repository.FavoriteRepository
import com.severo.pokeapi.podex.data.model.PokemonResultModel
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private var favoriteRepository: FavoriteRepository,
    private val coroutinesDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val pokemonFavoriteResultLiveData = MutableLiveData<SingleLiveEvent<Resource<List<PokemonResultModel>>>>()
    val navigateToDetails = MutableLiveData<SingleLiveEvent<Triple<PokemonResultModel, Int, String?>>>()
    val removePokemonFavorite = MutableLiveData<SingleLiveEvent<Resource<Int>>>()

    fun setupInit(){
        getPokemonFavorite()
    }

    fun afterDeletingCatchAllFavoritePokemons(){
        getPokemonFavorite()
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

    fun onItemRemoveClick(
        pokemonResultModel: PokemonResultModel,
        positionFavorite: Int
    ) {
        removePokemonFavorite(pokemonResultModel, positionFavorite)
    }

    private fun removePokemonFavorite(pokemonResultModel: PokemonResultModel, positionFavorite: Int) {
        viewModelScope.launch(coroutinesDispatcher) {
            removePokemonFavorite.postValue(SingleLiveEvent(Resource.loading()))
            try {
                favoriteRepository.deleteFavorite(pokemonResultModel).apply {
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