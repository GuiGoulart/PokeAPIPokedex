package com.severo.pokeapi.podex.model

import com.google.gson.annotations.SerializedName

data class StatsResponse(
    @SerializedName("base_stat")
    val base_stat: Int?,

    @SerializedName("effort")
    val effort: Int?,

    @SerializedName("stat")
    val statResponse: StatResponse?
)