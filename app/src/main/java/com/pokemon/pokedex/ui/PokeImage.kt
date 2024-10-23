package com.pokemon.pokedex.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pokemon.pokedex.R
import com.pokemon.pokedex.data.model.TypesItem
import com.pokemon.pokedex.util.GradientColor

@Composable
fun PokeImageView(
    cardModifier: Modifier,
    imageCanvasModifier: Modifier,
    url: String,
    types: List<TypesItem>?
) {
    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(4.dp)
    ) {
        SetDottedGradientBG(imageCanvasModifier = imageCanvasModifier, types = types)
        PokeImage(imageUrl = url, modifier = imageCanvasModifier.padding(2.dp))
    }
}

@Composable
fun SetDottedGradientBG(
    imageCanvasModifier: Modifier,
    types: List<TypesItem>?
) {
    val stroke = Stroke(
        width = 5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    val color = GradientColor.getColor(R.color.code_2E3156, LocalContext.current)

    Canvas(
        modifier = getGradientModifier(types, imageCanvasModifier)
    ) {
        drawRoundRect(color = color, style = stroke)
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokeImage(imageUrl: String, modifier: Modifier) {
    GlideImage(
        model = imageUrl,
        contentDescription = "image",
        modifier = modifier
    )
}

@Composable
fun getGradientModifier(list: List<TypesItem>?, orgModifier: Modifier): Modifier {
    var modifier = orgModifier
    list?.let {
        val colorList = GradientColor.getColorList(
            list,
            LocalContext.current
        )
        modifier = if (colorList.size >= 2) {
            modifier.background(
                brush = Brush.verticalGradient(
                    colorList,
                    1f,
                    Float.POSITIVE_INFINITY,
                    TileMode.Clamp
                )
            )
        } else {
            modifier.background(colorList[0])
        }
    }
    return modifier
}
