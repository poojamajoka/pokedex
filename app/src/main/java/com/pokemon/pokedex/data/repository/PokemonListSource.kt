package com.pokemon.pokedex.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pokemon.pokedex.data.model.ResultsItem
import com.pokemon.pokedex.data.network.DynamicUrl
import com.pokemon.pokedex.data.network.DynamicUrl.getCount
import com.pokemon.pokedex.data.network.DynamicUrl.setUrl

/**
 * Pokemon List and Detail api call
 */
class PokemonListSource(
    private val pokemonRepository: PokemonRepository
) : PagingSource<Int, ResultsItem>() {
    /**
     * load pokemon list data into pagination
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        return try {
            val nextPage = params.key ?: 1
            val pokeListResponse = pokemonRepository.getPokemonList(DynamicUrl.getUrl())
            val count = getCount()
            if (pokeListResponse != null) {
                pokeListResponse.next?.let { setUrl(it) }
                LoadResult.Page(
                    data = pokeListResponse.results,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = count.plus(1)
                )
            } else LoadResult.Error(Throwable("error"))

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        TODO("Not yet implemented")
    }
}