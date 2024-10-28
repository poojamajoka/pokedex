package com.pokemon.pokedex.ui.detail


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.pokemon.pokedex.R
import com.pokemon.pokedex.data.model.*
import com.pokemon.pokedex.ui.PokeImageView
import com.pokemon.pokedex.ui.home.MainViewModel
import com.pokemon.pokedex.util.GradientColor.getColor

import com.pokemon.pokedex.util.Utility.Companion.getFormattedId
import com.pokemon.pokedex.util.Utility.Companion.toUpperFirstChar

private lateinit var pokeDetail: ResultsItem
private lateinit var mainViewModel: MainViewModel
/*
description of pokemon
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DetailDialog(
    detail: ResultsItem,
    setShowDialog: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
    mainViewModel = viewModel
    pokeDetail = detail
    val pokeDescData by mainViewModel.pokeDescData.observeAsState()
    val pokePowerData by mainViewModel.pokePowerData.observeAsState()
    ObserveData()
    LaunchedEffect(true) {
        mainViewModel.getPokeDescriptionList(pokeDetail.pokeDetail.id)
        mainViewModel.getPokePowerList(pokeDetail.pokeDetail.id)
    }
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        PokeDetailView(setShowDialog, pokeDescData, pokePowerData)
    }
}

@Composable
fun ObserveData() {
    mainViewModel.pokeError.observeAsState()
    mainViewModel.pokeError.value?.let {
        if (it) {
            Toast.makeText(LocalContext.current, "error", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun PokeDetailView(
    setShowDialog: (Boolean) -> Unit,
    pokeDescData: PokemonDescriptionData?,
    pokePowerData: PokemonPower?
) {

    LazyColumn {
        item {
            TopDetailView(setShowDialog)
            Row(
                modifier = Modifier
                    .background(
                        color =
                        Color(ContextCompat.getColor(LocalContext.current, R.color.code_DEEDED))
                    )
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                pokeDetail.pokeDetail.sprites?.frontDefault?.let {
                    PokeImageView(
                        cardModifier = Modifier
                            .wrapContentHeight()
                            .padding(start = 20.dp, bottom = 30.dp),
                        imageCanvasModifier = Modifier
                            .width(150.dp)
                            .height(200.dp), it, pokeDetail.pokeDetail.types
                    )
                }
                PokeDescriptionView(pokeDescData)

            }
            PokeProfileView(pokeDescData, pokePowerData, pokeDetail,mainViewModel.pokeGenderData.value)

        }
        pokeStatTitle()
        pokeStats()
        pokeEvolutionView()
        pokeBottomButtonView(setShowDialog)
    }
}

fun LazyListScope.pokeStatTitle() {
    item {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .background(getColor(R.color.code_B0D2D2, LocalContext.current))
        ) {
            Text(
                modifier = Modifier.padding(start = 25.dp, top = 15.dp, bottom = 10.dp),
                text = stringResource(R.string.stats),
                style = TextStyle(
                    fontSize = 14.sp, color = Color(
                        ContextCompat.getColor(
                            LocalContext.current, R.color.code_2E3156
                        )
                    )
                ),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}


@Composable
fun TopDetailView(setShowDialog: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .background(
                color =
                Color(ContextCompat.getColor(LocalContext.current, R.color.code_DEEDED))
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 16.dp, top = 25.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 2.dp),
                text = pokeDetail.name.toUpperFirstChar(),
                style = TextStyle(
                    textAlign = TextAlign.Start, fontSize = 22.sp, color = Color(
                        ContextCompat.getColor(
                            LocalContext.current, R.color.code_2E3156
                        )
                    ),
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 20.dp),
                text = getFormattedId(pokeDetail.pokeDetail.id),
                style = TextStyle(
                    textAlign = TextAlign.Start, fontSize = 20.sp, color = Color(
                        ContextCompat.getColor(
                            LocalContext.current, R.color.code_5D5F7E
                        )
                    )
                ),
                fontWeight = FontWeight.Bold,
            )
        }
        Image(
            painterResource(id = R.drawable.ic_close),
            contentDescription = "closeIcon",
            modifier = Modifier
                .align(Alignment.TopEnd)
                //  .width(25.dp)
                //.height(25.dp)
                .padding(end = 16.dp, start = 5.dp, top = 25.dp)
                .clickable(onClick = { setShowDialog.invoke(false) })
        )
    }
}


@Composable
fun PokeDescriptionView(pokeDescData: PokemonDescriptionData?) {
    var desc = "NA"
    if (!pokeDescData?.formDescriptions.isNullOrEmpty()) {
        desc = pokeDescData?.formDescriptions.toString()
    }
    ExpandingText(
        modifier = Modifier
            .padding(start = 15.dp, end = 16.dp),
        text = desc
    )
}

fun LazyListScope.pokeStats() {
    pokeDetail.pokeDetail.stats?.let { statList ->
        items(statList.size) {
            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .background(getColor(R.color.code_B0D2D2, LocalContext.current))
            ) {
                Text(
                    modifier = Modifier.padding(start = 25.dp, bottom = 15.dp),
                    text = statList[it].stat?.name.toString().toUpperFirstChar(),
                    style = TextStyle(
                        fontSize = 14.sp, color = Color(
                            ContextCompat.getColor(
                                LocalContext.current, R.color.code_2E3156
                            )
                        )
                    ),

                    )
                LinearProgressIndicator(
                    progress = statList[it].baseStat / 100,
                    modifier = Modifier
                        .height(10.dp)
                        .padding(end = 20.dp)
                        .align(Alignment.CenterEnd),
                    backgroundColor = getColor(R.color.code_93B2B2, LocalContext.current),
                    color = getColor(R.color.code_2E3156, LocalContext.current)
                )
            }
        }
    }
}

fun LazyListScope.pokeEvolutionView() {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(getColor(R.color.code_DEEDED, LocalContext.current))
        ) {
            Text(
                modifier = Modifier.padding(start = 25.dp, top = 30.dp, bottom = 10.dp),
                text = stringResource(R.string.evoltuion_chain),
                style = TextStyle(
                    fontSize = 14.sp, color = Color(
                        ContextCompat.getColor(
                            LocalContext.current, R.color.code_2E3156
                        )
                    )
                ),
                fontWeight = FontWeight.Bold,
            )
            LazyRow(modifier = Modifier.padding(bottom = 15.dp, start = 22.dp)) {
                items(3) {
                    PokeImageView(
                        cardModifier = Modifier
                            .wrapContentHeight()
                            .padding(bottom = 30.dp),
                        imageCanvasModifier = Modifier
                            .width(70.dp)
                            .height(100.dp), "", pokeDetail.pokeDetail.types
                    )

                    Box(modifier = Modifier.height(100.dp)) {
                        Image(
                            painterResource(id = R.drawable.ic_arrow),
                            contentDescription = "arrow",
                            modifier = Modifier
                                .padding(5.dp)
                                .width(20.dp)
                                .height(20.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }

        }
    }

}

fun LazyListScope.pokeBottomButtonView(setShowDialog: (Boolean) -> Unit){
  item {
      Box(
          modifier = Modifier
              .fillMaxWidth().background(getColor(R.color.code_DEEDED, LocalContext.current))
              .padding(start = 25.dp, bottom = 30.dp, end = 25.dp)
      ) {

          pokeDetail.pokePrevious?.let {
              Button(
                  onClick = {
                      setShowDialog.invoke(false)
                  },
                  colors = ButtonDefaults.buttonColors(
                      backgroundColor = getColor(
                          R.color.code_2E3156,
                          LocalContext.current
                      )
                  )
              ) {
                  Image(
                      painterResource(id = R.drawable.ic_left_arrow),
                      contentDescription = "previous pokemon",
                      modifier = Modifier.size(20.dp)
                  )
                  Text(
                      text = pokeDetail.pokePrevious?.pokeDetail?.name.toString()
                          .toUpperFirstChar(),
                      Modifier.padding(start = 10.dp),
                      style = TextStyle(
                          fontSize = 14.sp, color = Color.White
                      )
                  )
              }
          }
          if (pokeDetail.pokeNext != null) {
              Button(
                  onClick = {
                      setShowDialog.invoke(false)
                  },
                  modifier = Modifier.align(Alignment.TopEnd),
                  colors = ButtonDefaults.buttonColors(
                      backgroundColor = getColor(
                          R.color.code_2E3156,
                          LocalContext.current
                      )
                  )
              ) {
                  Text(
                      text = pokeDetail.pokeNext?.pokeDetail?.name.toString()
                          .toUpperFirstChar(),
                      modifier = Modifier.padding(start = 8.dp, end = 25.dp),
                      style = TextStyle(
                          fontSize = 14.sp, color = Color.White
                      )
                  )
                  Image(
                      painterResource(id = R.drawable.ic_right_arrow),
                      contentDescription = "next pokemon",
                      modifier = Modifier.size(20.dp)
                  )

              }
          }
      }
  }
}


