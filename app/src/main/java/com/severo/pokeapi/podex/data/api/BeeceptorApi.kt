package com.severo.pokeapi.podex.data.api

import com.severo.pokeapi.podex.data.model.DetailModel
import retrofit2.http.Body
import retrofit2.http.POST

interface BeeceptorApi {
    @POST("pokemon/detail/favorite")
    suspend fun postWebhookBeeceptor(
        @Body detailModel: DetailModel
    )
}