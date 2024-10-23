package com.pokemon.pokedex.data.network

import com.pokemon.pokedex.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokemonApi {
    /**
     * return pokemon list
     */
    @GET
    suspend fun getPokemonList(@Url path: String): ResPokemonList

    /**
     * return pokemon details
     */
    @GET
    suspend fun getPokemonDetails(@Url path: String): PokeDetail

    /**
     * return pokemon gender data
     */
    @GET("api/v2/gender")
    suspend fun getPokemonGenderMasterData(): PokeGenderMasterData

    /**
     * return pokemon gender detail
     */
    @GET
    suspend fun getPokemonGenderDetail(@Url path: String): PokeGenderDetail

    /**
     * return pokemon description
     */
    @GET("api/v2/pokemon-species/{id}")
    suspend fun getPokemonDescription(@Path("id") id: String): PokemonDescriptionData

    /**
     * return pokemon strength/weakness
     */
    @GET("api/v2/type/{id}")
    suspend fun getPokemonPower(@Path("id") id: String): PokemonPower
}