package com.pokemon.pokedex.data.model

import com.google.gson.annotations.SerializedName

/**
 * poke detail
 */
data class PokeGenderDetail(

	@field:SerializedName("required_for_evolution")
	val requiredForEvolution: List<RequiredForEvolutionItem>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("pokemon_species_details")
	val pokemonSpeciesDetails: List<PokemonSpeciesDetailsItem>? = null
)

data class RequiredForEvolutionItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class PokemonSpeciesDetailsItem(

	@field:SerializedName("rate")
	val rate: Int? = null,

	@field:SerializedName("pokemon_species")
	val pokemonSpecies: PokemonSpecies? = null
)

data class PokemonSpecies(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
