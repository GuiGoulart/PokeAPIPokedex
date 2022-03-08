package com.severo.pokeapi.podex.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "pokemon_result")
data class PokemonResultModel(

    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String?
) : Serializable