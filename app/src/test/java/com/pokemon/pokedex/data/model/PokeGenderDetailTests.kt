package com.segment.analytics.kotlin.destinations.firebase

import com.pokemon.pokedex.data.model.PokeGenderDetail
import com.pokemon.pokedex.data.model.PokemonSpecies
import com.pokemon.pokedex.data.model.PokemonSpeciesDetailsItem
import com.pokemon.pokedex.data.model.RequiredForEvolutionItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith



class PokeGenderDetailTests {
    private var pokeGenderDetail1 = PokeGenderDetail(listOf(), "Poke1", 123, listOf())
    private var evolutionList: List<RequiredForEvolutionItem> =
        listOf(
            RequiredForEvolutionItem("Evolution Name1", "https://pokemonevoltion1url.com"),
            RequiredForEvolutionItem("Evolution Name2", "https://pokemonevoltion2url.com")
        )
    private var speciesList: List<PokemonSpeciesDetailsItem> =
        listOf(
            PokemonSpeciesDetailsItem(
                1,
                PokemonSpecies("Species Name1", "https://pokemonspecies1url.com")
            ),
        )
    private var pokeGenderDetail2 = PokeGenderDetail(evolutionList, "Poke2", 456, speciesList)

    @Test
    fun `pokemon gender details`() {
        assertEquals("Poke1", pokeGenderDetail1.name)
        assertEquals(123, pokeGenderDetail1.id)
    }

    @Test
    fun `pokemon gender detail with empty evolution list`() {
        assertNotNull(pokeGenderDetail2.requiredForEvolution)
        assertEquals(0, pokeGenderDetail1.requiredForEvolution?.size)
    }

    @Test
    fun `pokemon gender detail with empty species list`() {
        assertNotNull(pokeGenderDetail2.pokemonSpeciesDetails)
        assertEquals(0, pokeGenderDetail1.pokemonSpeciesDetails?.size)
    }

    @Test
    fun `pokemon gender detail with evolution list`() {
        assertEquals(2, pokeGenderDetail2.requiredForEvolution?.size)
        assertEquals("Evolution Name1", pokeGenderDetail2.requiredForEvolution!![0].name)
        assertEquals("Evolution Name2", pokeGenderDetail2.requiredForEvolution!![1].name)
        assertEquals(
            "https://pokemonevoltion1url.com",
            pokeGenderDetail2.requiredForEvolution!![0].url
        )
        assertEquals(
            "https://pokemonevoltion2url.com",
            pokeGenderDetail2.requiredForEvolution!![1].url
        )
    }

    @Test
    fun `pokemon gender detail with species list`() {
        assertEquals(1, pokeGenderDetail2.pokemonSpeciesDetails?.size)
        assertNotNull(pokeGenderDetail2.pokemonSpeciesDetails!![0].pokemonSpecies)
        assertEquals(1, pokeGenderDetail2.pokemonSpeciesDetails!![0].rate)
        assertEquals(
            "Species Name1",
            pokeGenderDetail2.pokemonSpeciesDetails!![0].pokemonSpecies!!.name
        )
        assertEquals(
            "https://pokemonspecies1url.com",
            pokeGenderDetail2.pokemonSpeciesDetails!![0].pokemonSpecies!!.url
        )
    }

}