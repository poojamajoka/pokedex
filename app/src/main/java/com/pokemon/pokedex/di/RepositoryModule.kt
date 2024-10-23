package com.pokemon.pokedex.di

import com.pokemon.pokedex.data.network.PokemonApi
import com.pokemon.pokedex.data.repository.PokemonRepository

import org.koin.dsl.module

val repositoryModule = module {
    single { createRepository(get()) }
}

fun createRepository(
   pokemonApi: PokemonApi
) : PokemonRepository = PokemonRepository(pokemonApi)