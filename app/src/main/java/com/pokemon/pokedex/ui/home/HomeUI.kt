package com.pokemon.pokedex.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.filter
import com.pokemon.pokedex.R
import com.pokemon.pokedex.data.model.ResultsItem
import com.pokemon.pokedex.ui.PokeImage
import com.pokemon.pokedex.ui.SetDottedGradientBG
import com.pokemon.pokedex.ui.detail.DetailDialog
import com.pokemon.pokedex.ui.home.filter.FilterData
import com.pokemon.pokedex.ui.home.filter.FilterDialog
import com.pokemon.pokedex.ui.model.FilterPokeTypeModel
import com.pokemon.pokedex.util.GradientColor.getColor
import com.pokemon.pokedex.util.Utility.Companion.getFormattedId
import com.pokemon.pokedex.util.Utility.Companion.toUpperFirstChar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * view model
 */
private lateinit var mainViewModel: MainViewModel

/**
 * filtered list for poke type
 */
private lateinit var filterTypeList: MutableState<ArrayList<FilterPokeTypeModel>>

/**
 * pokemon list
 */
private lateinit var pokemonList: Flow<PagingData<ResultsItem>>

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    mainViewModel = viewModel
    pokemonList = mainViewModel.pokemonList
    Column {
        TopView()
        Row(modifier = Modifier.padding(start = 14.dp, end = 14.dp, bottom = 10.dp)) {
            SearchView(modifier = Modifier.weight(1f))
            FilterView()
        }
        PokemonListView(pokemonList = pokemonList)
    }
}

@Composable
fun TopView() {
    Text(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(start = 14.dp, top = 20.dp, bottom = 10.dp),
        text = stringResource(R.string.pokedex),
        style = TextStyle(
            textAlign = TextAlign.Start, fontSize = 20.sp,
            color = getColor(R.color.code_2E3156, LocalContext.current),
            fontWeight = FontWeight.Bold
        )
    )
    Divider(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .width(1.dp)
            .padding(start = 14.dp, end = 14.dp, bottom = 10.dp)
    )
    Text(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(start = 14.dp, bottom = 25.dp, end = 14.dp),
        text = stringResource(R.string.home_page_detail),
        style = TextStyle(
            textAlign = TextAlign.Start, fontSize = 14.sp,
            color = getColor(R.color.code_5D5F7E,LocalContext.current)
        ),
        fontWeight = FontWeight.Bold,
    )
}


@Composable
fun SearchView(modifier: Modifier) {
    var searchText by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var isSearch by remember {
        mutableStateOf(false)
    }
    if (isSearch) {
        SearchApplied(searchStr = searchText.text)
    }
    TextField(
        modifier = modifier.background(
            shape = RoundedCornerShape(6.dp),
            color = getColor(R.color.code_C9DEE2, LocalContext.current)
        ),
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),

        value = searchText,
        onValueChange = { searchText = it },
        placeholder = {
            Text(
                text = stringResource(id = R.string.name_number),
                style = TextStyle(
                    fontSize = 14.sp,
                    color =getColor(R.color.code_5D5F7E80,LocalContext.current)
                )
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                modifier = Modifier.clickable {
                    isSearch = true
                }

            )
        },
    )

}


@SuppressLint("MutableCollectionMutableState")
@Composable
fun FilterView() {
    val filterPokeTypeData = FilterData.geFilterData(LocalContext.current)
    filterTypeList = remember {
        mutableStateOf(filterPokeTypeData)
    }

    val showFilterDialog = remember { mutableStateOf(false) }
    if (showFilterDialog.value) {
        FilterOnClick(showFilterDialog, mainViewModel, filterTypeList)
    }
    Column(
        modifier = Modifier
            .padding(start = 20.dp)
            .background(
                shape = RoundedCornerShape(8.dp),
                color = getColor(R.color.code_2E3156, LocalContext.current)
            )
            .clickable(onClick = {
                showFilterDialog.value = true
            })


    ) {
        Image(
            painterResource(id = R.drawable.filter),
            modifier = Modifier
                .width(70.dp)
                .height(55.dp)
                .padding(10.dp),
            contentDescription = null
        )
    }
}


@Composable
fun PokemonListView(pokemonList: Flow<PagingData<ResultsItem>>) {
    val lazyPokeItems = pokemonList.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp)
    ) {
        items(
            lazyPokeItems.itemCount
        ) { index ->
            Box(Modifier.padding(8.dp)) {
                val nextIndex = index + 1
                val previousIndex = index - 1
                var nextPokeDetail: ResultsItem? = null
                var previousPokeDetail: ResultsItem? = null
                if ((nextIndex < lazyPokeItems.itemCount)) {
                    nextPokeDetail = lazyPokeItems[index + 1]
                }
                if (previousIndex in 0 until index) {
                    previousPokeDetail = lazyPokeItems[index - 1]
                }
                lazyPokeItems[index]?.let {
                    PokeItem(
                        it,
                        nextPokeDetail, previousPokeDetail
                    )
                }
            }
        }

        lazyPokeItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyPokeItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyPokeItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokeItem(
    pokeItem: ResultsItem, nextPokeItem: ResultsItem?,
    previousPokeItem: ResultsItem?
) {
    pokeItem.pokeNext = nextPokeItem
    pokeItem.pokePrevious = previousPokeItem
    val modifier = Modifier
        .height(200.dp)
        .fillMaxSize()

    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        ItemOnClick(showDialog, pokeItem)
    }

    Box(
        modifier = modifier.clickable(onClick = { showDialog.value = true }),
    ) {
        Card(
            //padding for every side
            modifier = Modifier.padding(2.dp),
            elevation = 6.dp
        ) {
            SetDottedGradientBG(
                imageCanvasModifier = modifier,
                types = pokeItem.pokeDetail.types
            )
        }
        Column(
            modifier = Modifier.clickable(onClick = { showDialog.value = true }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PokeImage(
                pokeItem.pokeDetail.sprites?.frontDefault.toString(),
                Modifier
                    .height(170.dp)
                    .fillMaxSize()
                    .weight(1f),
            )
            Spacer(modifier = Modifier.height(9.dp))
            PokeTitle(pokeItem.name, pokeItem.pokeDetail.id)
        }

    }
}

@Composable
fun PokeTitle(
    title: String?,
    id: Int?
) {
    title?.let {
        Text(
            text = title.toUpperFirstChar(),
            maxLines = 1,
            style = TextStyle(
                textAlign = TextAlign.Start, fontSize = 14.sp,
                color = getColor(R.color.code_2E3156, LocalContext.current),
                fontWeight = FontWeight.Bold
            ),
        )
    }
    Text(
        text = getFormattedId(id),
        maxLines = 1,
        style = TextStyle(
            textAlign = TextAlign.Start, fontSize = 12.sp,
            color = getColor(R.color.code_2E3156, LocalContext.current)
        ),
        modifier = Modifier.padding(bottom = 12.dp, top = 2.dp)

    )
}

@Composable
fun ItemOnClick(showDialog: MutableState<Boolean>, pokeItem: ResultsItem) {
    DetailDialog(detail = pokeItem, setShowDialog = {
        showDialog.value = it
    }, mainViewModel)
}

@Composable
fun FilterOnClick(
    showDialog: MutableState<Boolean>,
    mainViewModel: MainViewModel,
    filterTypeList: MutableState<ArrayList<FilterPokeTypeModel>>
) {
    FilterDialog(setShowDialog = {
        showDialog.value = it
    }, mainViewModel, filterTypeList, filterApplied = filterApplied)
}

@Composable
fun SearchApplied(searchStr: String) {
//    pokemonList.collectAsLazyPagingItems().itemSnapshotList.filterList {
//        this?.pokeDetail?.id ==searchStr.toInt()
//    }
    //searchData(searchStr)
}

fun searchData(searchStr: String): Flow<PagingData<ResultsItem>> {
    val pokemonList = pokemonList.map { result ->
        result.filter { it.name == searchStr }
    }
    return pokemonList
}


val filterApplied = @Composable {
//    for (filterType: FilterPokeTypeModel in filterTypeList.value) {
//        pokemonList.collectAsLazyPagingItems().itemSnapshotList.forEach { pokeList ->
//            var list = pokeList?.pokeDetail?.types?.filter { it ->
//                it.type?.name == filterType.pokeName
//            }
//            list?.let {
//                if (list.isNotEmpty())
//                    pokeList?.isFilter = true
//            }
//        }
//    }
}
