package com.severo.pokeapi.podex.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.severo.pokeapi.podex.data.room.dao.FavoriteDao
import com.severo.pokeapi.podex.model.PokemonResultResponse

@Database(entities = [PokemonResultResponse::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}