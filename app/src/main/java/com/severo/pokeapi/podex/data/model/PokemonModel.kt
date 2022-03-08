package com.severo.pokeapi.podex.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonModel(
    @SerializedName("count")
    val count: Int?,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val resultRespons: List<PokemonResultModel>
) : Serializable