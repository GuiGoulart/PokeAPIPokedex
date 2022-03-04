package com.severo.pokeapi.podex.di

import com.severo.pokeapi.podex.data.repository.ApiRepository
import com.severo.pokeapi.podex.ui.viewModel.DetailsPokemonViewModel
import com.severo.pokeapi.podex.ui.viewModel.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private val loadFeature by lazy { loadKoinModules(appModule) }
internal fun injectFeature() = loadFeature

val appModule = module {

    factory { ApiRepository(get()) }
    factory { HomeViewModel(get()) }
    factory { DetailsPokemonViewModel(get()) }

}