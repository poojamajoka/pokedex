package com.pokemon.pokedex.data.repository

import com.pokemon.pokedex.data.model.*
import com.pokemon.pokedex.data.network.PokemonApi
import com.pokemon.pokedex.ui.home.ApiResponses
import com.pokemon.pokedex.ui.home.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PokemonRepositoryTest {
    @MockK
    private lateinit var pokemonApi: PokemonApi
    private lateinit var apiResponses: ApiRepoResponses
    private lateinit var pokemonRepository: PokemonRepository

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setUp() {
        apiResponses = ApiRepoResponses()
        pokemonRepository = PokemonRepository(pokemonApi)
    }

    @Test
    fun `api call for pokemon gender master data list return success response`() = runTest {
        coEvery {
            pokemonApi.getPokemonGenderMasterData()
        } returns apiResponses.getPokeGenderSuccessResponse()
        coEvery {
            pokemonApi.getPokemonGenderDetail("https://pokeapi.co/api/v2/gender/1/")
        } returns apiResponses.getPokeGenderFemaleSuccessResponse()
        coEvery {
            pokemonApi.getPokemonGenderDetail("https://pokeapi.co/api/v2/gender/2/")
        } returns apiResponses.getPokeGenderMaleSuccessResponse()
        coEvery {
            pokemonApi.getPokemonGenderDetail("https://pokeapi.co/api/v2/gender/3/")
        } returns apiResponses.getPokeGenderLessSuccessResponse()
        //when
        val res = apiResponses.getPokeGenderSuccessResponse()
        var result = PokeGenderMasterData()
        pokemonRepository.getPokemonGender().collect {
            result = it.data!!
        }
        //then
        assertEquals(res, result)
    }

    @Test
    fun `api call for pokemon gender female list return success response`() = runTest {
        coEvery {
            pokemonApi.getPokemonGenderDetail("https://pokeapi.co/api/v2/gender/1/")

        } returns apiResponses.getPokeGenderFemaleSuccessResponse()
        //when
        val expected = apiResponses.getPokeGenderFemaleSuccessResponse()
        val result = pokemonRepository.getPokeGenderDetail("https://pokeapi.co/api/v2/gender/1/")
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `api call for pokemon gender female list blank str return  response`() = runTest {
        coEvery {
            pokemonApi.getPokemonGenderDetail("")

        } returns PokeGenderDetail()
        //when
        val expected = PokeGenderDetail()
        val result = pokemonRepository.getPokeGenderDetail("")
        //then
        assertEquals(expected, result)
    }


    @Test
    fun `api call for pokemon gender male list return success response`() = runTest {
        coEvery {
            pokemonApi.getPokemonGenderDetail("https://pokeapi.co/api/v2/gender/2/")

        } returns apiResponses.getPokeGenderMaleSuccessResponse()

        //when
        val expected = apiResponses.getPokeGenderMaleSuccessResponse()
        val result = pokemonRepository.getPokeGenderDetail("https://pokeapi.co/api/v2/gender/2/")
        //then
        assertEquals(expected, result)
    }


    @Test
    fun `api call for pokemon gender male list blank str return response`() = runTest {
        coEvery {
            pokemonApi.getPokemonGenderDetail(" ")

        } returns apiResponses.getPokeGenderMaleSuccessResponse()

        //when
        val expected = apiResponses.getPokeGenderMaleSuccessResponse()
        val result = pokemonRepository.getPokeGenderDetail(" ")
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `api call for pokemon gender less list return success response`() = runTest {
        coEvery {
            pokemonApi.getPokemonGenderDetail("https://pokeapi.co/api/v2/gender/3/")

        } returns apiResponses.getPokeGenderLessSuccessResponse()

        //when
        val expected = apiResponses.getPokeGenderLessSuccessResponse()
        val result = pokemonRepository.getPokeGenderDetail("https://pokeapi.co/api/v2/gender/3/")
        //then
        assertEquals(expected, result)
    }


    @Test
    fun `api call for pokemon gender less list blank str return response`() = runTest {
        coEvery {
            pokemonApi.getPokemonGenderDetail("")

        } returns apiResponses.getPokeGenderLessSuccessResponse()

        //when
        val expected = apiResponses.getPokeGenderLessSuccessResponse()
        val result = pokemonRepository.getPokeGenderDetail("")
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `api call for pokemon description list return success response`() = runTest {
        coEvery {
            pokemonApi.getPokemonDescription("6")

        } returns apiResponses.getPokemonDescriptionSuccess()

        //when
        val expected = apiResponses.getPokemonDescriptionSuccess()
        var result = PokemonDescriptionData()
        pokemonRepository.getPokemonDescription("6").collect {
            result = it.data!!
        }
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `api call for pokemon power list return success response`() = runTest {
        coEvery {
            pokemonApi.getPokemonPower("6")
        } returns apiResponses.getPokemonPowerSuccess()

        //when
        val expected = apiResponses.getPokemonPowerSuccess()
        var result = PokemonPower()
        pokemonRepository.getPokemonPower("6").collect {
            result = it.data!!
        }
        //then
        assertEquals(expected, result)
    }


}