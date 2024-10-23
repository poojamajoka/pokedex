package com.pokemon.pokedex.data.repository

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.pokemon.pokedex.data.model.PokeGenderDetail
import com.pokemon.pokedex.data.model.PokeGenderMasterData
import com.pokemon.pokedex.data.model.PokemonDescriptionData
import com.pokemon.pokedex.data.model.PokemonPower
import com.pokemon.pokedex.data.network.ApiResHandling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File


class ApiRepoResponses {

    /**
     * get pokemon description failure
     */
    fun getPokeDescFailureResponse(): String {
        return "Not found"
    }

    /**
     * id=6
     * get pokemon description success response
     */
    fun getPokemonDescriptionSuccess(): PokemonDescriptionData {
        val jsonString = getJson("pokeDescriptionData.json")
        val pokeDescription = fromJson(
            jsonString,
            object : TypeToken<PokemonDescriptionData>() {}.type as Class<Any>
        )
        return pokeDescription as PokemonDescriptionData
    }

    /**
     * get pokemon description failure
     */
    fun getPokePowerFailureResponse(): PokemonPower{
        return PokemonPower()
    }

    /**
     * id=6
     * get pokemon description success response
     */
    fun getPokemonPowerSuccess(): PokemonPower {
        val jsonString = getJson("pokePowerData.json")
        val pokePower = fromJson(
            jsonString,
            object : TypeToken<PokemonPower>() {}.type as Class<Any>
        )
        return pokePower as PokemonPower

    }

    /**
     * get pokemon gender data failure
     */
    fun getPokeGenderFailureResponse(): Flow<ApiResHandling<PokeGenderMasterData>> {
        return flow {
            val errorMessage = "Not found"
            emit(ApiResHandling.Error(errorMessage))
        }
    }

    /**
     * get pokemon gender data success
     */
    fun getPokeGenderSuccessResponse(): PokeGenderMasterData {
        val jsonStringMaster = getJson("pokeGenderMaster.json")
        val pokeGenderMasterData = fromJson(
            jsonStringMaster,
            object : TypeToken<PokeGenderMasterData>() {}.type as Class<Any>
        )
        val jsonStringFemale = getJson("femaleGender.json")
        val pokeGenderFemaleData = fromJson(
            jsonStringFemale,
            object : TypeToken<PokeGenderDetail>() {}.type as Class<Any>
        )
        val jsonStringMale = getJson("maleGender.json")
        val pokeGenderMaleData = fromJson(
            jsonStringMale,
            object : TypeToken<PokeGenderDetail>() {}.type as Class<Any>
        )
        val jsonStringGenderLess = getJson("genderLess.json")
        val pokeGenderLessData = fromJson(
            jsonStringGenderLess,
            object : TypeToken<PokeGenderDetail>() {}.type as Class<Any>
        )

        val pokeGenMasterList = pokeGenderMasterData as PokeGenderMasterData
        pokeGenderMasterData.results!![0].genderDetail =
            pokeGenderFemaleData as PokeGenderDetail
        pokeGenderMasterData.results!![1].genderDetail = pokeGenderMaleData as PokeGenderDetail
        pokeGenderMasterData.results!![2].genderDetail = pokeGenderLessData as PokeGenderDetail
        return pokeGenMasterList

    }

    /**
     * get pokemon female gender data success
     */
    fun getPokeGenderFemaleSuccessResponse(): PokeGenderDetail {
        val jsonStringFemale = getJson("femaleGender.json")
        val pokeGenderFemaleData = fromJson(
            jsonStringFemale,
            object : TypeToken<PokeGenderDetail>() {}.type as Class<Any>
        )

        return pokeGenderFemaleData as PokeGenderDetail

    }

    /**
     * get pokemon male gender data success
     */
    fun getPokeGenderMaleSuccessResponse(): PokeGenderDetail {
        val jsonStringMale = getJson("maleGender.json")
        val pokeGenderMaleData = fromJson(
            jsonStringMale,
            object : TypeToken<PokeGenderDetail>() {}.type as Class<Any>
        )
        return pokeGenderMaleData as PokeGenderDetail
    }

    /**
     * get pokemon  gender less data success
     */
    fun getPokeGenderLessSuccessResponse(): PokeGenderDetail {
        val jsonStringGenderLess = getJson("genderLess.json")
        val pokeGenderLessData = fromJson(
            jsonStringGenderLess,
            object : TypeToken<PokeGenderDetail>() {}.type as Class<Any>
        )
        return pokeGenderLessData as PokeGenderDetail
    }

    /**
     * return json string
     */
    private fun getJson(resourceName: String): String {
        val file = File("src/test/resources/$resourceName")
        return String(file.readBytes())
    }

    /**
     * return model from json
     */
    fun <T> fromJson(json: String, clazz: Class<T>): T {
        val gson = GsonBuilder().create()
        return gson.fromJson(json, clazz)
    }

}
