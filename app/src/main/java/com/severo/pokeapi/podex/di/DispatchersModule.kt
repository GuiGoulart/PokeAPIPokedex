package com.severo.pokeapi.podex.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dispatchersModule = module {

    factory{ Dispatchers.IO }

}