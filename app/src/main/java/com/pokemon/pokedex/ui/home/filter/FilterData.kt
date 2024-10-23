package com.pokemon.pokedex.ui.home.filter

import android.content.Context
import com.pokemon.pokedex.R
import com.pokemon.pokedex.ui.model.FilterPokeTypeModel

object FilterData {
    /**
     * return filter data for type
     */
    fun geFilterData(context: Context): ArrayList<FilterPokeTypeModel> {
        val filterPokeList = ArrayList<FilterPokeTypeModel>()
        val filterStrArray = context.resources.getStringArray(R.array.filterPokeType)
        for (strItem: String in filterStrArray) {
            filterPokeList.add(FilterPokeTypeModel(strItem, false))
        }
        return filterPokeList
    }
}