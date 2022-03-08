package com.severo.pokeapi.podex.data.model

import com.google.gson.annotations.SerializedName

data class StatsModel(
    @SerializedName("base_stat")
    val base_stat: Int?,

    @SerializedName("effort")
    val effort: Int?,

    @SerializedName("stat")
    val statModel: StatModel?
)