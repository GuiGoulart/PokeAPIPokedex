package com.severo.pokeapi.podex.di

import com.severo.pokeapi.podex.util.BEECEPTOR_API_RETROFIT
import com.severo.pokeapi.podex.util.POKE_API_RETROFIT
import org.koin.core.qualifier.named
import org.koin.dsl.module

val apiModules = module {

    factory { providePokemonApi(get(named(POKE_API_RETROFIT))) }
    factory { provideBeeceptorApi(get(named(BEECEPTOR_API_RETROFIT))) }

}