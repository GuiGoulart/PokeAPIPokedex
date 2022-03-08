package com.severo.pokeapi.podex.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.severo.pokeapi.podex.data.api.PokemonApi
import com.severo.pokeapi.podex.data.model.PokemonResultModel
import com.severo.pokeapi.podex.util.SEARCH_LOAD_SIZE
import com.severo.pokeapi.podex.util.STARTING_OFFSET_INDEX
import java.io.IOException

class PokemonDataSource(private val pokemonApi: PokemonApi, private val searchString: String?) :
    PagingSource<Int, PokemonResultModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResultModel> {
        val offset = params.key ?: STARTING_OFFSET_INDEX
        val loadSize = if (searchString == null) params.loadSize else SEARCH_LOAD_SIZE
        return try {
            val data = pokemonApi.getPokemons(loadSize, offset)
            val filteredData = if (searchString != null) {
                data.resultRespons.filter { it.name?.contains(searchString, true) ?: false }
            } else {
                data.resultRespons
            }
            LoadResult.Page(
                data = filteredData,
                prevKey = if (offset == STARTING_OFFSET_INDEX) null else offset - loadSize,
                nextKey = if (data.next == null) null else offset + loadSize
            )
        } catch (throwable: Throwable) {
            var exception = throwable
            if (exception is IOException) {
                exception = IOException("Please check internet connection")
            }
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonResultModel>): Int? {
        return state.anchorPosition
    }
}