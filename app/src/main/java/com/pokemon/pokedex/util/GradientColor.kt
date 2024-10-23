package com.pokemon.pokedex.util

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.pokemon.pokedex.R
import com.pokemon.pokedex.data.model.TypesItem


object GradientColor {
    /**
     * get background color on the basis of type
     */
    fun getColorList(types: List<TypesItem>, context: Context): ArrayList<Color> {
        val colorList = ArrayList<Color>()
        // var color: Color
        types.forEach { typeItem: TypesItem ->
            typeItem.type.let { type ->
                run {
                    type?.let {
                        type.name.let {
                            colorList.add(getColorForType(it, context))
                        }
                    }
                }
            }

            // colorList.add(color)
        }
        return colorList
    }

    fun getColorForType(type: String, context: Context): Color {
        val color: Color
        when (type) {
            "normal" -> color = Color(ContextCompat.getColor(context, R.color.normal))
            "rock" -> color = Color(ContextCompat.getColor(context, R.color.rock))
            "water" -> color = Color(ContextCompat.getColor(context, R.color.water))
            "dragon" -> color = Color(ContextCompat.getColor(context, R.color.dragon))
            "fighting" -> color = Color(ContextCompat.getColor(context, R.color.fighting))
            "bug" -> color = Color(ContextCompat.getColor(context, R.color.bug))
            "grass" -> color = Color(ContextCompat.getColor(context, R.color.grass))
            "dark" -> color = Color(ContextCompat.getColor(context, R.color.dark))
            "flying" -> color = Color(ContextCompat.getColor(context, R.color.flying))
            "ghost" -> color = Color(ContextCompat.getColor(context, R.color.ghost))
            "electric" -> color = Color(ContextCompat.getColor(context, R.color.electric))
            "fairy" -> color = Color(ContextCompat.getColor(context, R.color.fairy))
            "poison" -> color = Color(ContextCompat.getColor(context, R.color.poison))
            "steel" -> color = Color(ContextCompat.getColor(context, R.color.steel))
            "physic" -> color = Color(ContextCompat.getColor(context, R.color.physic))
            "ground" -> color = Color(ContextCompat.getColor(context, R.color.ground))
            "unknown" -> color = Color(ContextCompat.getColor(context, R.color.unknown))
            "fire" -> color = Color(ContextCompat.getColor(context, R.color.fire))
            "ice" -> color = Color(ContextCompat.getColor(context, R.color.ice))
            "shadow" -> color = Color(ContextCompat.getColor(context, R.color.shadow))
            else -> color = Color(ContextCompat.getColor(context, R.color.unknown))
        }
        return color
    }

    /**
     * return color
     */
    fun getColor(color: Int, context: Context): Color =
        Color(ContextCompat.getColor(context, color))

}
