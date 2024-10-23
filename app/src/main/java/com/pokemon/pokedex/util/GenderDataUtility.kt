package com.pokemon.pokedex.util

import com.pokemon.pokedex.data.model.GenderData
import com.pokemon.pokedex.data.model.PokeGenderMasterData
import com.pokemon.pokedex.data.model.PokemonSpeciesDetailsItem
import com.pokemon.pokedex.util.Utility.Companion.toUpperFirstChar

object GenderDataUtility {

    fun getGenderOfPokemon(pokeMasterData: PokeGenderMasterData?, pokeName: String): String {
        var gender = ""
        pokeMasterData?.results?.let {
            pokeMasterData.results.forEach { genderMaster: GenderData ->
                genderMaster.genderDetail.pokemonSpeciesDetails?.let {
                    for (genderDetail: PokemonSpeciesDetailsItem in genderMaster.genderDetail.pokemonSpeciesDetails!!) {
                        if (genderDetail.pokemonSpecies?.name == pokeName) {
                            gender = if (gender.isEmpty()) {
                                genderMaster.name.toUpperFirstChar()
                            } else {
                                "$gender, ${genderMaster.name.toUpperFirstChar()}"
                            }
                            break
                        }
                    }
                }
            }
        }
        return gender
    }
}