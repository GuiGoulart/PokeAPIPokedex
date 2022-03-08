package com.severo.pokeapi.podex.ui.adapter.listener

import com.severo.pokeapi.podex.data.model.PokemonResultModel

interface PokemonListener {
    fun clickDetails(pokemonResultModel: PokemonResultModel, dominantColor: Int, picture: String?)
}