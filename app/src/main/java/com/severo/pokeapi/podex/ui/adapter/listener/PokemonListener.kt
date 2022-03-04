package com.severo.pokeapi.podex.ui.adapter.listener

import com.severo.pokeapi.podex.model.PokemonResultResponse

interface PokemonListener {
    fun clickDetails(pokemonResultResponse: PokemonResultResponse, dominantColor: Int, picture: String?)
}