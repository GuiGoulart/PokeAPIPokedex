package com.severo.pokeapi.podex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.severo.pokeapi.podex.data.dataSource.PokemonDataSource
import com.severo.pokeapi.podex.data.api.PokemonApi
import com.severo.pokeapi.podex.data.model.DetailModel

class PokeApiRepository(private var pokemonApi: PokemonApi) {

    fun getPokemon(searchString: String?) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 25),
        pagingSourceFactory = {
            PokemonDataSource(pokemonApi, searchString)
        }
    )

    suspend fun getSinglePokemon(id: Int): DetailModel = pokemonApi.getSinglePokemon(id)


}