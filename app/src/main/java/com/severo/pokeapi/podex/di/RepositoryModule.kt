package com.severo.pokeapi.podex.di

import com.severo.pokeapi.podex.data.repository.BeeceptorRepository
import com.severo.pokeapi.podex.data.repository.FavoriteRepository
import com.severo.pokeapi.podex.data.repository.PokeApiRepository
import com.severo.pokeapi.podex.data.room.dao.FavoriteDao
import com.severo.pokeapi.podex.ui.viewModel.DetailsPokemonViewModel
import com.severo.pokeapi.podex.ui.viewModel.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val repositoryModule = module {

    factory { PokeApiRepository(get()) }
    factory { BeeceptorRepository(get()) }
    factory { FavoriteRepository(get()) }

}