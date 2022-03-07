package com.severo.pokeapi.podex.data.repository

import com.severo.pokeapi.podex.data.room.dao.FavoriteDao
import com.severo.pokeapi.podex.model.PokemonResultResponse

class FavoriteRepository(var favoriteDao: FavoriteDao) {

    fun favoriteAll(): List<PokemonResultResponse> {
        return favoriteDao.getAll()
    }

    fun insertFavorite(pokemonResultResponse: PokemonResultResponse) {
        favoriteDao.insertFavorite(pokemonResultResponse)
    }

    fun deleteFavorite(pokemonResultResponse: PokemonResultResponse) {
        favoriteDao.deleteFavorite(pokemonResultResponse)
    }
}