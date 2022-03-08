package com.severo.pokeapi.podex.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.severo.pokeapi.podex.util.Resource
import com.severo.pokeapi.podex.util.SingleLiveEvent
import com.severo.pokeapi.podex.data.base.BaseViewModel
import com.severo.pokeapi.podex.data.repository.BeeceptorRepository
import com.severo.pokeapi.podex.data.repository.FavoriteRepository
import com.severo.pokeapi.podex.data.repository.PokeApiRepository
import com.severo.pokeapi.podex.data.model.PokemonResultModel
import com.severo.pokeapi.podex.data.model.DetailModel
import com.severo.pokeapi.podex.util.extensions.extractId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailsPokemonViewModel(
    private var pokemonRepositoryPoke: PokeApiRepository,
    private var beeceptorRepository: BeeceptorRepository,
    private var favoriteRepository: FavoriteRepository,
    private val coroutinesDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val pokemonDetailResultLiveData = MutableLiveData<SingleLiveEvent<Resource<DetailModel>>>()
    val pokemonFavoriteResultLiveData = MutableLiveData<SingleLiveEvent<Resource<PokemonResultModel>>>()
    val beeceptorResultLiveData = MutableLiveData<SingleLiveEvent<Resource<Unit>>>()
    val insertPokemonFavorite = MutableLiveData<SingleLiveEvent<Resource<Unit>>>()
    val removePokemonFavorite = MutableLiveData<SingleLiveEvent<Resource<Unit>>>()

    fun setupInit(url: String, name: String){
        getPokemonFavorite(name)
        getPokemonDetail(url)
    }

    fun onClickAddFavoritePokemon(
        detailModel: DetailModel,
        pokemonResultModel: PokemonResultModel
    ){
        postBeeceptor(detailModel)
        insertPokemonFavorite(pokemonResultModel)
    }

    fun onClickRemoveFavoritePokemon(
        detailModel: DetailModel,
        pokemonResultModel: PokemonResultModel
    ){
        postBeeceptor(detailModel)
        removePokemonFavorite(pokemonResultModel)
    }

    private fun insertPokemonFavorite(pokemonResultModel: PokemonResultModel) {
        viewModelScope.launch(coroutinesDispatcher) {
            insertPokemonFavorite.postValue(SingleLiveEvent(Resource.loading()))
            try {
                favoriteRepository.insertFavorite(pokemonResultModel).apply {
                    getPokemonFavorite(pokemonResultModel.name.toString())
                    insertPokemonFavorite.postValue(SingleLiveEvent(Resource.success(this)))
                }
            }catch (e: Exception){
                insertPokemonFavorite.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }

    private fun removePokemonFavorite(pokemonResultModel: PokemonResultModel) {
        viewModelScope.launch(coroutinesDispatcher) {
            removePokemonFavorite.postValue(SingleLiveEvent(Resource.loading()))
            try {
                favoriteRepository.deleteFavorite(pokemonResultModel).apply {
                    removePokemonFavorite.postValue(SingleLiveEvent(Resource.success(this)))
                }
            }catch (e: Exception){
                removePokemonFavorite.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }

    private fun getPokemonFavorite(name: String) {
        viewModelScope.launch(coroutinesDispatcher) {
            val response = favoriteRepository.getPokemon(name)
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

    fun postBeeceptor(detailModel: DetailModel){
        beeceptorResultLiveData.postValue(SingleLiveEvent(Resource.loading()))

        viewModelScope.launch(coroutinesDispatcher) {
            try {
                beeceptorRepository.postWebhookBeeceptor(detailModel)
                beeceptorResultLiveData.postValue(SingleLiveEvent(Resource.success(Unit)))
            }catch (e: Exception){
                Log.d("ErrorBeeceptor", e.message.toString())
            }
        }
    }
}