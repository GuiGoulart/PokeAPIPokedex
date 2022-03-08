package com.severo.pokeapi.podex.data.repository

import com.severo.pokeapi.podex.data.model.PokemonResultModel
import com.severo.pokeapi.podex.data.room.dao.FavoriteDao

class FavoriteRepository(var favoriteDao: FavoriteDao) {

    suspend fun favoriteAll(): List<PokemonResultModel> {
        return favoriteDao.getAll()
    }

    suspend fun getPokemon(name: String): PokemonResultModel {
        return favoriteDao.getPokemon(name)
    }

    suspend fun insertFavorite(pokemonResultRequest: PokemonResultModel) {
        favoriteDao.insertFavorite(pokemonResultRequest)
    }

    suspend fun deleteFavorite(pokemonResultRequest: PokemonResultModel) {
        favoriteDao.deleteFavorite(pokemonResultRequest)
    }
}