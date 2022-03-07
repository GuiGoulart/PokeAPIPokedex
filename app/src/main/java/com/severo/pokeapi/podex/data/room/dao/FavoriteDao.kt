package com.severo.pokeapi.podex.data.room.dao

import androidx.room.*
import com.severo.pokeapi.podex.model.PokemonResultResponse

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM pokemon_result")
    fun getAll(): List<PokemonResultResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(pokemonResultResponse: PokemonResultResponse)

    @Delete
    fun deleteFavorite(pokemonResultResponse: PokemonResultResponse)
}