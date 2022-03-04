package com.severo.pokeapi.podex.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.severo.pokeapi.podex.data.service.PokemonApi
import com.severo.pokeapi.podex.model.PokemonResultResponse
import com.severo.pokeapi.podex.util.SEARCH_LOAD_SIZE
import com.severo.pokeapi.podex.util.STARTING_OFFSET_INDEX
import java.io.IOException

class PokemonDataSource(private val pokemonApi: PokemonApi, private val searchString: String?) :
    PagingSource<Int, PokemonResultResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResultResponse> {
        val offset = params.key ?: STARTING_OFFSET_INDEX
        val loadSize = if (searchString == null) params.loadSize else SEARCH_LOAD_SIZE
        return try {
            val data = pokemonApi.getPokemons(loadSize, offset)
            val filteredData = if (searchString != null) {
                data.resultResponses.filter { it.name.contains(searchString, true) }
            } else {
                data.resultResponses
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

    override fun getRefreshKey(state: PagingState<Int, PokemonResultResponse>): Int? {
        return state.anchorPosition
    }
}