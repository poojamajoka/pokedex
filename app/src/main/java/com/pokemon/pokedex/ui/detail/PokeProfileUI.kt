package com.pokemon.pokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.pokemon.pokedex.R
import com.pokemon.pokedex.data.model.*
import com.pokemon.pokedex.util.GenderDataUtility
import com.pokemon.pokedex.util.GradientColor
import com.pokemon.pokedex.util.Utility.Companion.toUpperFirstChar

/**
 * profile of pokemon with pokemon power and gender
 */
@Composable
fun PokeProfileView(
    pokeDescData: PokemonDescriptionData?,
    pokePowerData: PokemonPower?,
    pokeDetail: ResultsItem,
    genderData: PokeGenderMasterData?
) {
    Column(
        modifier = Modifier
            .background(
                color =
                Color(ContextCompat.getColor(LocalContext.current, R.color.code_DEEDED))
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                PokeHeight(pokeDetail)
                PokeGender(pokeDescData?.name, genderData)
                PokeAbilities(pokeDetail)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                PokeWeight(pokeDetail)
                PokeEggGroups(pokeDescData)
                PokeTypes(pokeDetail)
            }
        }
        PokeWeakness(pokePowerData)
    }
}

@Composable
fun PokeHeight(pokeDetail: ResultsItem) {
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 2.dp),
            text = stringResource(R.string.height),
            style = TextStyle(
                textAlign = TextAlign.Start, fontSize = 14.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_2E3156
                    )
                )
            ),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = pokeDetail.pokeDetail.height.toString(),
            style = TextStyle(
                textAlign = TextAlign.Start, fontSize = 12.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_5D5F7E
                    )
                )
            ),
        )
    }
}

@Composable
fun PokeWeight(pokeDetail: ResultsItem) {
    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Text(
            modifier = Modifier
                .padding(bottom = 2.dp),
            text = stringResource(R.string.weight),
            style = TextStyle(
                textAlign = TextAlign.End, fontSize = 14.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_2E3156
                    )
                )
            ),
            fontWeight = FontWeight.Bold,

            )
        Text(
            text = pokeDetail.pokeDetail.weight.toString(),
            style = TextStyle(
                textAlign = TextAlign.End, fontSize = 12.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_5D5F7E
                    )
                )
            ),
        )
    }
}

@Composable
fun PokeGender(name: String?, genderData: PokeGenderMasterData?) {
    var gender = ""
    name?.let {
        gender = GenderDataUtility.getGenderOfPokemon(genderData, it)
    }
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 2.dp),
            text = stringResource(R.string.gender),
            style = TextStyle(
                textAlign = TextAlign.Start, fontSize = 14.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_2E3156
                    )
                )
            ),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = gender,
            style = TextStyle(
                textAlign = TextAlign.Start, fontSize = 12.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_5D5F7E
                    )
                )
            ),
        )
    }
}

@Composable
fun PokeEggGroups(pokeDescData: PokemonDescriptionData?) {
    var eggGroup = ""
    pokeDescData?.eggGroups.let { eggList ->
        eggList?.let {
            for (item: EggGroupsItem in eggList) {
                eggGroup = if (eggGroup.isEmpty()) {
                    item.name.toUpperFirstChar()
                } else "$eggGroup, ${item.name.toUpperFirstChar()}"
            }
        }
    }
    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Text(
            modifier = Modifier
                .padding(bottom = 2.dp),
            text = stringResource(R.string.egg_group),
            style = TextStyle(
                textAlign = TextAlign.End, fontSize = 14.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_2E3156
                    )
                )
            ),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = eggGroup,
            style = TextStyle(
                textAlign = TextAlign.End, fontSize = 12.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_5D5F7E
                    )
                )
            ),
        )
    }
}

@Composable
fun PokeAbilities(pokeDetail: ResultsItem) {
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 2.dp),
            text = stringResource(R.string.abilities),
            style = TextStyle(
                textAlign = TextAlign.Start, fontSize = 14.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_2E3156
                    )
                )
            ),
            fontWeight = FontWeight.Bold,
        )
        var abilities = ""
        pokeDetail.pokeDetail.abilities?.let { abilitiesList ->
            for (listItem: AbilitiesItem in abilitiesList) {
                listItem.ability?.let { ability ->
                    ability.name?.let {
                        abilities = if (abilities.isEmpty()) {
                            it.toUpperFirstChar()
                        } else "$abilities , ${it.toUpperFirstChar()}"
                    }
                }
            }
        }

        Text(
            text = abilities,
            style = TextStyle(
                textAlign = TextAlign.Start, fontSize = 12.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_5D5F7E
                    )
                )
            ),
        )
    }
}

@Composable
fun PokeTypes(pokeDetail: ResultsItem) {
    Column {
        Text(
            modifier = Modifier
                .padding(bottom = 2.dp),
            text = stringResource(R.string.types),
            style = TextStyle(
                textAlign = TextAlign.End, fontSize = 14.sp, color = Color(
                    ContextCompat.getColor(
                        LocalContext.current, R.color.code_2E3156
                    )
                )
            ),
            fontWeight = FontWeight.Bold,
        )
        pokeDetail.pokeDetail.types?.let { typeList ->
            LazyRow {
                items(typeList.size, key = { it }) {
                    var color = Color(ContextCompat.getColor(LocalContext.current, R.color.unknown))
                    typeList[it].type?.run {
                        color = GradientColor.getColorForType(name, LocalContext.current)
                    }
                    Box(modifier = Modifier.padding(5.dp)) {
                        Card(
                            modifier = Modifier.border(
                                1.dp,
                                color = color,
                                shape = RoundedCornerShape(4.dp)
                            ),
                            elevation = 4.dp,
                            backgroundColor = color

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .defaultMinSize(30.dp),
                                text = typeList[it].type?.name.toString().toUpperFirstChar(),
                                style = TextStyle(
                                    textAlign = TextAlign.Center, fontSize = 12.sp, color = Color(
                                        ContextCompat.getColor(
                                            LocalContext.current, R.color.code_5D5F7E
                                        )
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun PokeWeakness(pokePowerData: PokemonPower?) {
    pokePowerData?.damageRelations?.doubleDamageFrom.let { damageList ->
        damageList?.let {
            Column(modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 2.dp),
                    text = stringResource(R.string.weakness),
                    style = TextStyle(
                        textAlign = TextAlign.End, fontSize = 14.sp, color = Color(
                            ContextCompat.getColor(
                                LocalContext.current, R.color.code_2E3156
                            )
                        )
                    ),
                    fontWeight = FontWeight.Bold,
                )
                LazyRow {
                    items(damageList.size) {
                        var color: Color
                        damageList[it].name.run {
                            color = GradientColor.getColorForType(this, LocalContext.current)
                        }
                        Box(modifier = Modifier.padding(5.dp)) {
                            Card(
                                modifier = Modifier.border(
                                    1.dp,
                                    color = GradientColor.getColor(
                                        R.color.code_2E3156,
                                        LocalContext.current
                                    ),
                                    shape = RoundedCornerShape(4.dp)
                                ), elevation = 4.dp, backgroundColor = color

                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .defaultMinSize(30.dp),
                                    text = damageList[it].name.toUpperFirstChar(),
                                    style = TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                        color = Color(
                                            ContextCompat.getColor(
                                                LocalContext.current, R.color.code_5D5F7E
                                            )
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}