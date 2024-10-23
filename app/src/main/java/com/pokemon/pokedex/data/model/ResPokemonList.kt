package com.pokemon.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class ResPokemonList(

    @SerializedName("next")
    val next: String? = null,

    @SerializedName("previous")
    val previous: Any? = null,

    @SerializedName("count")
    val count: Int? = null,

    @SerializedName("results")
    val results: List<ResultsItem>
)

data class ResultsItem(

    @SerializedName("name")
    val name: String = "",

    @SerializedName("url")
    val url: String? = null,

    var pokeDetail: PokeDetail,

    var pokeNext:ResultsItem?,

    var pokePrevious:ResultsItem?,

    var isFilter:Boolean=false

)
