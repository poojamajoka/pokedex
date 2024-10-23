package com.pokemon.pokedex.data.network

import com.pokemon.pokedex.util.Constant.POKEMON_LIST_URL


object DynamicUrl {
    private var pokemonListUrl = POKEMON_LIST_URL
    private var currentPageNum = 0

    /**
     * set pokemon list url by appending new offset page num
     */
    fun setUrl(url: String) {
        pokemonListUrl = url
    }

    /**
     * get pokemon list url
     */
    fun getUrl(): String {
        return pokemonListUrl
    }

    /**
     * increase page num by 1
     */
    fun getCount(): Int {
        return currentPageNum++
    }

}
