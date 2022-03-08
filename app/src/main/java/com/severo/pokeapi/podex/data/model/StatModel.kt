package com.severo.pokeapi.podex.data.model

import com.google.gson.annotations.SerializedName

data class StatModel(
    @SerializedName("name")
    val name: String?,

    @SerializedName("url")
    val url: String?
)