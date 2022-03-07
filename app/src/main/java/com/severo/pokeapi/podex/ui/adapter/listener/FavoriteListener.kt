package com.severo.pokeapi.podex.ui.adapter.listener

import com.severo.pokeapi.podex.model.PokemonResultResponse

interface FavoriteListener {
    fun clickDetails(pokemonResultResponse: PokemonResultResponse, dominantColor: Int, picture: String?)
    fun clickDelete(pokemonResultResponse: PokemonResultResponse, positionFavorite: Int)
}