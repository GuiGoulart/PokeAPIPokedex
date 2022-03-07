package com.severo.pokeapi.podex.di

import android.app.Application
import androidx.room.Room
import com.severo.pokeapi.podex.data.room.dao.FavoriteDao
import com.severo.pokeapi.podex.data.room.database.FavoriteDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val favoriteDatabaseModules = module {
    fun provideDataBase(application: Application): FavoriteDatabase {
        return Room.databaseBuilder(application, FavoriteDatabase::class.java, "USERDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: FavoriteDatabase): FavoriteDao {
        return dataBase.favoriteDao()
    }

    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }

}