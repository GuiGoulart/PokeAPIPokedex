package com.severo.pokeapi.podex.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.severo.pokeapi.podex.R
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import com.severo.pokeapi.podex.data.base.BaseViewModel
import com.severo.pokeapi.podex.data.repository.BeeceptorRepository
import com.severo.pokeapi.podex.data.repository.PokeApiRepository
import com.severo.pokeapi.podex.model.SinglePokemonResponse
import com.severo.pokeapi.podex.util.BASE_URL_BEECEPTOR
import com.severo.pokeapi.podex.util.extensions.extractId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsPokemonViewModel(
    private var pokemonRepositoryPoke: PokeApiRepository,
    private var beeceptorRepository: BeeceptorRepository,
    private val coroutinesDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private var favoritePokemon = false

    val pokemonDetailResultLiveData = MutableLiveData<SingleLiveEvent<Resource<SinglePokemonResponse>>>()
    val beeceptorResultLiveData = MutableLiveData<SingleLiveEvent<Resource<Unit>>>()
    val onClickPokemonDetailLiveData = MutableLiveData<SingleLiveEvent<Boolean>>()

    fun onClickFavoritePokemon(singlePokemonResponse: SinglePokemonResponse){
        favoritePokemon = !favoritePokemon
        onClickPokemonDetailLiveData.postValue(SingleLiveEvent(favoritePokemon))
        postBeeceptor(singlePokemonResponse)
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