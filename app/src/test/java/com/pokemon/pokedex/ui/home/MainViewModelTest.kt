package com.pokemon.pokedex.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pokemon.pokedex.data.model.PokeGenderMasterData
import com.pokemon.pokedex.data.model.PokemonDescriptionData
import com.pokemon.pokedex.data.model.PokemonPower
import com.pokemon.pokedex.data.repository.PokemonRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var pokemonRepository: PokemonRepository
    private lateinit var apiResponses: ApiResponses
    init {
        MockKAnnotations.init(this)
    }
    @Before
    fun setUp() {
       // MockitoAnnotations.initMocks(this)
        pokemonRepository = mockk()
        apiResponses = ApiResponses()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `api call for pokemon description list return failure response`() = runTest {
        val mainViewModel = MainViewModel(pokemonRepository)
        coEvery {
            pokemonRepository.getPokemonDescription("0")
        } returns apiResponses.getPokeDescFailureResponse()
        // when
        mainViewModel.getPokeDescriptionList(0)
        mainViewModel.pokeError.observeForever {}
        //then
        val result = mainViewModel.pokeError.value
        val expectedResult = true
        assertEquals(result, expectedResult)
    }

    @Test
    fun `api call for pokemon description list return success response`() = runTest {
        val mainViewModel = MainViewModel(pokemonRepository)
        coEvery {
            pokemonRepository.getPokemonDescription("6")
        } returns apiResponses.getPokemonDescriptionSuccess()
        // when
        mainViewModel.getPokeDescriptionList(6)
        mainViewModel.pokeDescData.observeForever {}
        //then
        var poke = PokemonDescriptionData()
        apiResponses.getPokemonDescriptionSuccess().collect {
            poke = it.data!!
        }
        val result = mainViewModel.pokeDescData.value
        val expectedRes = poke
        assertEquals(result, expectedRes)
    }


    @Test
    fun `api call for pokemon power list return success response`() = runTest {
        val mainViewModel = MainViewModel(pokemonRepository)
        coEvery {
            pokemonRepository.getPokemonPower("6")
        } returns apiResponses.getPokemonPowerSuccess()
        // when
        mainViewModel.getPokePowerList(6)
        mainViewModel.pokePowerData.observeForever {}
        //then
        var poke = PokemonPower()
        apiResponses.getPokemonPowerSuccess().collect {
            poke = it.data!!
        }
        val res = mainViewModel.pokePowerData.value
        val expectedRes = poke
        assertEquals(res, expectedRes)
    }

    @Test
    fun `api call for pokemon power list return failure response`() = runTest {
        val mainViewModel = MainViewModel(pokemonRepository)
        coEvery {
            pokemonRepository.getPokemonPower("0")
        } returns apiResponses.getPokePowerFailureResponse()
        // when
        mainViewModel.getPokePowerList(0)
        mainViewModel.pokePowerData.observeForever {}
        //then
        val result = mainViewModel.pokeError.value
        val expectedResult = true
        assertEquals(result, expectedResult)
    }

    @Test
    fun `api call for pokemon gender list return success response`() = runTest {
        val mainViewModel = MainViewModel(pokemonRepository)
        coEvery {
            pokemonRepository.getPokemonGender()
        } returns apiResponses.getPokeGenderSuccessResponse()
        // when
        mainViewModel.getGenderList()
        mainViewModel.pokeGenderData.observeForever {}
        //then
        var poke = PokeGenderMasterData()
        apiResponses.getPokeGenderSuccessResponse().collect {
            poke = it.data!!
        }
        val res = mainViewModel.pokeGenderData.value
        val expectedRes = poke
        assertEquals(res, expectedRes)
    }

    @Test
    fun `api call for pokemon gender list return failure response`() = runTest {
        val mainViewModel = MainViewModel(pokemonRepository)
        coEvery {
            pokemonRepository.getPokemonGender()
        } returns apiResponses.getPokeGenderFailureResponse()
        // when
        mainViewModel.getGenderList()
        mainViewModel.pokeGenderData.observeForever {}
        //then
        val result = mainViewModel.pokeError.value
        val expectedResult = true
        assertEquals(result, expectedResult)
    }



    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}