package com.severo.pokeapi.podex.ui.viewModel

import androidx.lifecycle.MutableLiveData
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import com.severo.pokeapi.podex.data.base.BaseViewModel
import com.severo.pokeapi.podex.data.repository.ApiRepository
import com.severo.pokeapi.podex.model.SinglePokemonResponse
import com.severo.pokeapi.podex.util.extensions.extractId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsPokemonViewModel(private var pokemonRepository: ApiRepository) : BaseViewModel() {

    val pokemonDetailResultLiveData = MutableLiveData<SingleLiveEvent<Resource<SinglePokemonResponse>>>()

    fun getPokemonDetail(url: String) {
        pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.loading()))

        uiScope.launch(Dispatchers.IO) {
            val id = url.extractId()
            try {
                val response = pokemonRepository.getSinglePokemon(id)
                if (response.isSuccessful) pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.success(response.body())))
                else {
                    pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.error()))
                }
            }catch (e: Exception){
                pokemonDetailResultLiveData.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }
}