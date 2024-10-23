package com.pokemon.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class PokemonPower(

    @field:SerializedName("generation")
    val generation: Generation? = null,

    @field:SerializedName("game_indices")
    val gameIndices: List<GameIndicesItem?>? = null,

    @field:SerializedName("move_damage_class")
    val moveDamageClass: MoveDamageClass? = null,

    @field:SerializedName("names")
    val names: List<NamesItem?>? = null,

    @field:SerializedName("damage_relations")
    val damageRelations: DamageRelations? = null,

    @field:SerializedName("moves")
    val moves: List<MovesItem?>? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("past_damage_relations")
    val pastDamageRelations: List<Any?>? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

data class DoubleDamageToItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)


data class DoubleDamageFromItem(

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("url")
    val url: String? = null
)

data class DamageRelations(

    @field:SerializedName("no_damage_from")
    val noDamageFrom: List<Any?>? = null,


    @field:SerializedName("no_damage_to")
    val noDamageTo: List<Any?>? = null,

    @field:SerializedName("double_damage_to")
    val doubleDamageTo: List<DoubleDamageToItem?>? = null,

    @field:SerializedName("double_damage_from")
    val doubleDamageFrom: List<DoubleDamageFromItem>? = null
)


data class MoveDamageClass(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)




