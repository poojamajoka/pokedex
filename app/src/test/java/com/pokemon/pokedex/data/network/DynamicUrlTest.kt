package com.pokemon.pokedex.data.network

import com.pokemon.pokedex.util.Constant
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class DynamicUrlTest {
    private var count = 0

    @Before
    fun setUp() {
        DynamicUrl.setUrl(Constant.POKEMON_LIST_URL)
    }

    @Test
    fun getUrl() {
        assertEquals(Constant.POKEMON_LIST_URL, DynamicUrl.getUrl())
    }

    @Test
    fun getCount() {
    assertEquals(count,DynamicUrl.getCount())
    }
}