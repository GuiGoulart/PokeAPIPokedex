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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsPokemonViewModel(
    private var pokemonRepositoryPoke: PokeApiRepository,
    private var beeceptorRepository: BeeceptorRepository) : BaseViewModel() {

    private var favoritePokemon = false

    val pokemonDetailResultLiveData = MutableLiveData<SingleLiveEvent<Resource<SinglePokemonResponse>>>()
    private val beeceptorResultLiveData = MutableLiveData<SingleLiveEvent<Resource<Void>>>()
    val onClickPokemonDetailLiveData = MutableLiveData<SingleLiveEvent<Boolean>>()

    fun onClickFavoritePokemon(singlePokemonResponse: SinglePokemonResponse){
        favoritePokemon = !favoritePokemon
        onClickPokemonDetailLiveData.postValue(SingleLiveEvent(favoritePokemon))
        postBeeceptor(singlePokemonResponse)
    }

    fun getPokemonDetail(url: String) {
        pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.loading()))

        viewModelScope.launch(Dispatchers.IO) {
            val id = url.extractId()
            try {
                val response = pokemonRepositoryPoke.getSinglePokemon(id)
                if (response.isSuccessful) pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.success(response.body())))
                else {
                    pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.error()))
                }
            }catch (e: Exception){
                pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }

    fun postBeeceptor(singlePokemonResponse: SinglePokemonResponse){
        beeceptorResultLiveData.postValue(SingleLiveEvent(Resource.loading()))

        viewModelScope.launch(Dispatchers.IO) {
            try {
                beeceptorRepository.postWebhookBeeceptor(singlePokemonResponse)
            }catch (e: Exception){
                Log.d("ErrorBeeceptor", e.message.toString())
            }
        }
    }
}