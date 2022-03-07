package com.severo.pokeapi.podex.di

import com.severo.pokeapi.podex.ui.viewModel.DetailsPokemonViewModel
import com.severo.pokeapi.podex.ui.viewModel.FavoriteViewModel
import com.severo.pokeapi.podex.ui.viewModel.HomeViewModel
import org.koin.dsl.module

val viewModelModules = module {

    factory { HomeViewModel(get(), get()) }
    factory { DetailsPokemonViewModel(get(), get(), get(), get()) }
    factory { FavoriteViewModel(get(), get()) }

}