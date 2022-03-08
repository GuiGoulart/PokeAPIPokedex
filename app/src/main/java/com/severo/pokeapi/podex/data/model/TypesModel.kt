package com.severo.pokeapi.podex.data.model

import com.google.gson.annotations.SerializedName

data class TypesModel(
    @SerializedName("slot")
    val slot: Int?,

    @SerializedName("type")
    val typeModel: TypeModel?
)