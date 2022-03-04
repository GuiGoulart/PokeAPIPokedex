package com.severo.pokeapi.podex.model

import com.google.gson.annotations.SerializedName

data class SinglePokemonResponse(
    @SerializedName("sprites")
    val spritesResponse: SpritesResponse,

    @SerializedName("stats")
    val stats: List<StatsResponse>,

    @SerializedName("types")
    val types: List<TypesResponse>,

    @SerializedName("height")
    val height: Int,

    @SerializedName("weight")
    val weight: Int
)