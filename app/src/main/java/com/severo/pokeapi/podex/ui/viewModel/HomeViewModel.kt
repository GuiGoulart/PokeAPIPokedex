package com.severo.pokeapi.podex.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import com.severo.pokeapi.podex.data.base.BaseViewModel
import com.severo.pokeapi.podex.data.repository.PokeApiRepository
import com.severo.pokeapi.podex.model.PokemonResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private var pokemonRepositoryPoke: PokeApiRepository) : BaseViewModel() {

    val pokemonResultLiveData = MutableLiveData<SingleLiveEvent<Resource<PagingData<PokemonResultResponse>>>>()

    init {
        getPokemons(null)
    }

    fun getPokemons(searchString: String?) {
        pokemonResultLiveData.postValue(SingleLiveEvent(Resource.loading()))

        uiScope.launch(Dispatchers.IO) {
            try {
                val response = pokemonRepositoryPoke.getPokemon(searchString)
                response.flow.collectLatest {
                    pokemonResultLiveData.postValue(SingleLiveEvent(Resource.success(it)))
                }
            }catch (e: Exception){
                pokemonResultLiveData.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }
}