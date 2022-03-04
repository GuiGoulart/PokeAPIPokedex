package com.severo.pokeapi.podex.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val resultResponses: List<PokemonResultResponse>
) : Serializable