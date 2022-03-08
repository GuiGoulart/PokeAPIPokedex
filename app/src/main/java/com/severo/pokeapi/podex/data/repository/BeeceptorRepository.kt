package com.severo.pokeapi.podex.data.repository

import com.severo.pokeapi.podex.data.api.BeeceptorApi
import com.severo.pokeapi.podex.data.model.DetailModel

class BeeceptorRepository(private var beeceptorApi: BeeceptorApi) {

    suspend fun postWebhookBeeceptor(detailModel: DetailModel) = beeceptorApi.postWebhookBeeceptor(detailModel)

}