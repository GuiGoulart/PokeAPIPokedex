package com.severo.pokeapi.podex.data.room.dao

import androidx.room.*
import com.severo.pokeapi.podex.data.model.PokemonResultModel

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM pokemon_result")
    suspend fun getAll(): List<PokemonResultModel>

    @Query("SELECT * FROM pokemon_result WHERE name == :name")
    suspend fun getPokemon(name: String): PokemonResultModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(pokemonResultRequest: PokemonResultModel)

    @Delete
    suspend fun deleteFavorite(pokemonResultRequest: PokemonResultModel)
}