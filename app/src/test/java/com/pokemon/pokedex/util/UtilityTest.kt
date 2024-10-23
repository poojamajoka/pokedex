package com.pokemon.pokedex.util

import com.pokemon.pokedex.util.Utility.Companion.toUpperFirstChar
import org.junit.Assert.*
import org.junit.Test

class UtilityTest{

    @Test
    fun `send single name digit id and get success of added 00 in it`(){
        val result="001"
        assertEquals(result,Utility.getFormattedId(1))
    }
    @Test
    fun `send double name digit id and get same as it is in success`(){
        val result="11"
        assertEquals(result,Utility.getFormattedId(11))
    }
    @Test
    fun `send  digit id null and get blank str success`(){
        val result=""
        assertEquals(result,Utility.getFormattedId(null))
    }

    @Test
    fun `send lowercase string and get capitalize first letter str`(){
        val testStr="Test"
        assertEquals(testStr,testStr.toUpperFirstChar())
    }
}