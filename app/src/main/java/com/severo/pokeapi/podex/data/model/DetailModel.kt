package com.severo.pokeapi.podex.data.model

import com.google.gson.annotations.SerializedName

data class DetailModel(
    @SerializedName("sprites")
    val spritesModel: SpritesModel?,

    @SerializedName("stats")
    val stats: List<StatsModel>?,

    @SerializedName("types")
    val types: List<TypesModel>?,

    @SerializedName("height")
    val height: Int?,

    @SerializedName("weight")
    val weight: Int?
)