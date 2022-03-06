package com.severo.pokeapi.podex.data.repository

import com.severo.pokeapi.podex.data.service.BeeceptorApi
import com.severo.pokeapi.podex.model.SinglePokemonResponse
import retrofit2.Response

class BeeceptorRepository(private var beeceptorApi: BeeceptorApi) {

    suspend fun postWebhookBeeceptor(singlePokemonResponse: SinglePokemonResponse) = beeceptorApi.postWebhookBeeceptor(singlePokemonResponse)

}