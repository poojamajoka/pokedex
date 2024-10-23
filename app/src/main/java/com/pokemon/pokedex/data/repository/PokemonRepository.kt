package com.pokemon.pokedex.data.repository

import com.pokemon.pokedex.data.model.*
import com.pokemon.pokedex.data.network.ApiResHandling
import com.pokemon.pokedex.data.network.PokemonApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonRepository(
    private val pokemonApi: PokemonApi
) {
    /**
     * pokemon list and pokemon detail api hit
     * return pokemon list with detail data
     */
    suspend fun getPokemonList(url: String): ResPokemonList? {
        var pokemonList: ResPokemonList? = null
        val flowDetail = flow {
            val pokeList = pokemonApi.getPokemonList(url)
            pokeList.results.let { list ->
                repeat(list.size) {
                    val resultItem = pokeList.results[it]
                    val input = resultItem.url
                    input?.let { detailUrl ->
                        val pokeDetail = pokemonApi.getPokemonDetails(detailUrl)
                        resultItem.pokeDetail = pokeDetail
                    }

                }
                emit(pokeList)
            }

        }
        flowDetail.collect { finalPokeList ->
            pokemonList = finalPokeList
        }
        return pokemonList
    }

    /**
     * gender master data and detail api
     * return gender master data with detail info
     */
    suspend fun getPokemonGender(): Flow<ApiResHandling<PokeGenderMasterData>> {
        return flow {
            try {
                val pokeGenMasterList = pokemonApi.getPokemonGenderMasterData()
                pokeGenMasterList.results?.let { list ->
                    repeat(list.size) {
                        val resultItem = pokeGenMasterList.results[it]
                        val input = resultItem.url
                        input?.let { detailUrl ->
                            resultItem.genderDetail = getPokeGenderDetail(detailUrl)
                        }
                    }
                    emit(ApiResHandling.Success(pokeGenMasterList))
                }

            } catch (e: Throwable) {
                emit(ApiResHandling.Error(e.toString()))
            }
        }
    }

   suspend fun getPokeGenderDetail(detailUrl:String): PokeGenderDetail {
      return pokemonApi.getPokemonGenderDetail(detailUrl)
   }
    /**
     * return pokemon description data
     */

    suspend fun getPokemonDescription(id: String): Flow<ApiResHandling<PokemonDescriptionData>> {
        return flow {
            try {
                val pokeList = pokemonApi.getPokemonDescription(id)
                emit(ApiResHandling.Success(pokeList))
            } catch (e: Throwable) {
                emit(ApiResHandling.Error(e.toString()))
            }
        }
    }

    /**
     * get Pokemon Power data
     */
    suspend fun getPokemonPower(id: String): Flow<ApiResHandling<PokemonPower>> {
        return flow {
            try {
                val pokeList = pokemonApi.getPokemonPower(id)
                emit(ApiResHandling.Success(pokeList))
            } catch (e: Throwable) {
                emit(ApiResHandling.Error(e.toString()))
            }

        }
    }

}

