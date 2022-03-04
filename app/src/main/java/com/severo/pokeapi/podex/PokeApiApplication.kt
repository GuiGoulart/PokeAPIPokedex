package com.severo.pokeapi.podex

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.severo.pokeapi.podex.di.appModule
import com.severo.pokeapi.podex.di.appModuleService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

val mainModules = listOf(appModuleService, appModule)

class PokeApiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = applicationContext
        init()
    }

    private fun init() {
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PokeApiApplication)
            modules(mainModules)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: Context? = null
            private set
    }
}