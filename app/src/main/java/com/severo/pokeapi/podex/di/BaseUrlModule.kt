package com.severo.pokeapi.podex.di

import com.severo.pokeapi.podex.util.BASE_URL_BEECEPTOR
import com.severo.pokeapi.podex.util.BASE_URL_POKEAPI
import org.koin.core.qualifier.named
import org.koin.dsl.module

val baseUrlModule = module {

    factory(named("pokeApiBaseUrl")) { RetrofitBaseUrl(BASE_URL_POKEAPI) }
    factory(named("beeceptorApiBaseUrl")) {  RetrofitBaseUrl(BASE_URL_BEECEPTOR) }

}