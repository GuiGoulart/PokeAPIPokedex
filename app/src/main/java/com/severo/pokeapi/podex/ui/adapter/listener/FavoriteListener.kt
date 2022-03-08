package com.severo.pokeapi.podex.ui.adapter.listener

import com.severo.pokeapi.podex.data.model.PokemonResultModel

interface FavoriteListener {
    fun clickDetails(pokemonResultModel: PokemonResultModel, dominantColor: Int, picture: String?)
    fun clickDelete(pokemonResultModel: PokemonResultModel, positionFavorite: Int)
}