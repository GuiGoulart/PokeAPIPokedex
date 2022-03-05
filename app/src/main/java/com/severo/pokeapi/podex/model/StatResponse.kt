package com.severo.pokeapi.podex.model

import com.google.gson.annotations.SerializedName

data class StatResponse(
    @SerializedName("name")
    val name: String?,

    @SerializedName("url")
    val url: String?
)