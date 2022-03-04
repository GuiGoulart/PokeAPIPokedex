package com.severo.pokeapi.podex.data.service

import com.severo.pokeapi.podex.model.SinglePokemonResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface BeeceptorApi {
    @POST
    suspend fun postWebhookBeeceptor(
        @Url url: String,
        @Body singlePokemonResponse: SinglePokemonResponse
    ): Response<Void>
}