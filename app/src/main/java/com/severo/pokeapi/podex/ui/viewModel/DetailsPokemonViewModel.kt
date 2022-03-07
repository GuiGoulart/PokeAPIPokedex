package com.severo.pokeapi.podex.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.severo.pokeapi.podex.R
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import com.severo.pokeapi.podex.data.base.BaseViewModel
import com.severo.pokeapi.podex.data.repository.BeeceptorRepository
import com.severo.pokeapi.podex.data.repository.FavoriteRepository
import com.severo.pokeapi.podex.data.repository.PokeApiRepository
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.model.SinglePokemonResponse
import com.severo.pokeapi.podex.util.BASE_URL_BEECEPTOR
import com.severo.pokeapi.podex.util.extensions.extractId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsPokemonViewModel(
    private var pokemonRepositoryPoke: PokeApiRepository,
    private var beeceptorRepository: BeeceptorRepository,
    private var favoriteRepository: FavoriteRepository,
    private val coroutinesDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val pokemonDetailResultLiveData = MutableLiveData<SingleLiveEvent<Resource<SinglePokemonResponse>>>()
    val pokemonFavoriteResultLiveData = MutableLiveData<SingleLiveEvent<Resource<List<PokemonResultResponse>>>>()
    val beeceptorResultLiveData = MutableLiveData<SingleLiveEvent<Resource<Unit>>>()
    val insertPokemonFavorite = MutableLiveData<SingleLiveEvent<Resource<Unit>>>()
    val removePokemonFavorite = MutableLiveData<SingleLiveEvent<Resource<Unit>>>()

    fun setupInit(url: String){
        getPokemonFavorite()
        getPokemonDetail(url)
    }

    fun onClickAddFavoritePokemon(
        singlePokemonResponse: SinglePokemonResponse,
        pokemonResultResponse: PokemonResultResponse
    ){
        postBeeceptor(singlePokemonResponse)
        insertPokemonFavorite(pokemonResultResponse)
    }

    fun onClickRemoveFavoritePokemon(
        singlePokemonResponse: SinglePokemonResponse,
        pokemonResultResponse: PokemonResultResponse
    ){
        postBeeceptor(singlePokemonResponse)
        removePokemonFavorite(pokemonResultResponse)
    }

    private fun insertPokemonFavorite(pokemonResultResponse: PokemonResultResponse) {
        viewModelScope.launch(coroutinesDispatcher) {
            insertPokemonFavorite.postValue(SingleLiveEvent(Resource.loading()))
            try {
                favoriteRepository.insertFavorite(pokemonResultResponse).apply {
                    getPokemonFavorite()
                    insertPokemonFavorite.postValue(SingleLiveEvent(Resource.success(this)))
                }
            }catch (e: Exception){
                insertPokemonFavorite.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }

    private fun removePokemonFavorite(pokemonResultResponse: PokemonResultResponse) {
        viewModelScope.launch(coroutinesDispatcher) {
            removePokemonFavorite.postValue(SingleLiveEvent(Resource.loading()))
            try {
                favoriteRepository.deleteFavorite(pokemonResultResponse).apply {
                    removePokemonFavorite.postValue(SingleLiveEvent(Resource.success(this)))
                }
            }catch (e: Exception){
                removePokemonFavorite.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }

    private fun getPokemonFavorite() {
        viewModelScope.launch(coroutinesDispatcher) {
            val response = favoriteRepository.favoriteAll()
            pokemonFavoriteResultLiveData.postValue(SingleLiveEvent(Resource.success(response)))
        }
    }

    fun getPokemonDetail(url: String) {
        viewModelScope.launch(coroutinesDispatcher) {
            val id = url.extractId()
            pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.loading()))
            try {
                val response = pokemonRepositoryPoke.getSinglePokemon(id)
                pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.success(response)))
            }catch (e: Exception){
                pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }

    fun postBeeceptor(singlePokemonResponse: SinglePokemonResponse){
        beeceptorResultLiveData.postValue(SingleLiveEvent(Resource.loading()))

        viewModelScope.launch(coroutinesDispatcher) {
            try {
                beeceptorRepository.postWebhookBeeceptor(singlePokemonResponse)
                beeceptorResultLiveData.postValue(SingleLiveEvent(Resource.success(Unit)))
            }catch (e: Exception){
                Log.d("ErrorBeeceptor", e.message.toString())
            }
        }
    }
}