package com.severo.pokeapi.podex.data.api

import com.severo.pokeapi.podex.data.model.PokemonModel
import com.severo.pokeapi.podex.data.model.DetailModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon/")
    suspend fun getPokemons(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): PokemonModel

    @GET("pokemon/{id}/")
    suspend fun getSinglePokemon(
        @Path("id") id: Int
    ): DetailModel
}