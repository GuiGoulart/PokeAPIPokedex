package com.severo.pokeapi.podex.model

import com.google.gson.annotations.SerializedName

data class TypesResponse(
    @SerializedName("slot")
    val slot: Int?,

    @SerializedName("type")
    val typeResponse: TypeResponse?
)