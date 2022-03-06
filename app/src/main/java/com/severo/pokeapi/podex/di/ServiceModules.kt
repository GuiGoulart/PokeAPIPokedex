package com.severo.pokeapi.podex.di

import com.severo.pokeapi.podex.data.service.AuthInterceptor
import com.severo.pokeapi.podex.util.BEECEPTOR_API_BASE_URL
import com.severo.pokeapi.podex.util.BEECEPTOR_API_RETROFIT
import com.severo.pokeapi.podex.util.POKE_API_BASE_URL
import com.severo.pokeapi.podex.util.POKE_API_RETROFIT
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModules = module {

    factory { AuthInterceptor() }
    factory { providesOkHttpClient() }
    single(named(POKE_API_RETROFIT)) { provideRetrofit(get(), get(named(POKE_API_BASE_URL))) }
    single(named(BEECEPTOR_API_RETROFIT)) { provideRetrofit(get(), get(named(BEECEPTOR_API_BASE_URL))) }

}