package com.pokemon.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class PokeGenderMasterData(

	@field:SerializedName("next")
	val next: Any? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<GenderData>? = null
)

data class GenderData(

	@field:SerializedName("name")
	val name: String = "",

	@field:SerializedName("url")
	val url: String? = null,

	var genderDetail: PokeGenderDetail,

	var isSelected:Boolean=false
)
