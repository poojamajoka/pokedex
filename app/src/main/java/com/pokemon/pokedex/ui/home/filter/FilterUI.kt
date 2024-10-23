package com.pokemon.pokedex.ui.home.filter

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.pokemon.pokedex.R
import com.pokemon.pokedex.ui.home.MainViewModel
import com.pokemon.pokedex.ui.model.FilterPokeTypeModel
import com.pokemon.pokedex.util.GradientColor.getColor

/**
 * filter collapse and expand state
 */
private var isFilterTypeViewVisible = mutableStateOf(false)

/**
 * selected filter list
 */
private val selectedFilterList = ArrayList<FilterPokeTypeModel>()

/**
 * observe filter type check
 */
private var isCheckChange = mutableStateOf(false)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FilterDialog(
    setShowDialog: (Boolean) -> Unit,
    viewModel: MainViewModel,
    filterTypeList: MutableState<ArrayList<FilterPokeTypeModel>>,
    filterApplied: @Composable () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Column {
            FilterView(setShowDialog, filterTypeList, filterApplied, viewModel)

        }

    }
}

@Composable
fun FilterView(
    setShowDialog: (Boolean) -> Unit,
    filterTypeList: MutableState<ArrayList<FilterPokeTypeModel>>,
    filterApplied: @Composable () -> Unit,
    mainViewModel: MainViewModel
) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp)
            .background(color = Color.White)
            .border(
                1.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Box {
            Column {
                FilterTopView(setShowDialog)
                FilterTypeView(filterTypeList)
                FilterGenderView(mainViewModel.pokeGenderData)
            }
            BottomView(modifier = Modifier.align(Alignment.BottomEnd),
                filterApplied,setShowDialog)
        }
    }


}

@Composable
fun FilterTopView(setShowDialog: (Boolean) -> Unit) {
    Column {
        Row(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp, top = 16.dp, bottom = 10.dp
            )
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.filters),
                style = TextStyle(
                    textAlign = TextAlign.Start, fontSize = 22.sp, color = Color(
                        ContextCompat.getColor(
                            LocalContext.current, R.color.code_2E3156
                        )
                    ),
                    fontWeight = FontWeight.Bold
                )
            )
            Image(
                painterResource(id = R.drawable.ic_close),
                contentDescription = "closeIcon",
                modifier = Modifier
                    .clickable(onClick = {
                        setShowDialog.invoke(false)
                    })
            )
        }
        Divider(
            color = getColor(R.color.code_2E3156_alpha, LocalContext.current),
            modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
                .padding(start = 14.dp, end = 14.dp, bottom = 20.dp)
        )
    }
}

@Composable
fun BottomView(
    modifier: Modifier,
    filterApplied: @Composable () -> Unit,
    setShowDialog: (Boolean) -> Unit
) {
    val isFilterApply = remember {
        mutableStateOf(false)
    }
    if (isFilterApply.value) run {
        filterApplied.invoke()
    }
    Card(elevation = 4.dp, modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = {
                    setShowDialog.invoke(false)
                },
                border = BorderStroke(
                    1.dp,
                    color = getColor(R.color.code_2E3156, LocalContext.current)
                ),

                modifier = Modifier
                    .weight(1f)
                    .padding(20.dp)

            ) {
                Text(
                    text = stringResource(R.string.reset),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = getColor(R.color.code_2E3156, LocalContext.current)
                    )
                )
            }
            Button(
                onClick = {
                    isFilterApply.value = true
                    setShowDialog.invoke(false)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = getColor(
                        R.color.code_2E3156,
                        LocalContext.current
                    )
                )
            ) {
                Text(
                    text = stringResource(R.string.apply),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp, color = Color.White
                    )
                )

            }
        }
    }
}


@Composable
fun FilterTypeView(filterTypeList: MutableState<ArrayList<FilterPokeTypeModel>>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .border(
                1.dp,
                color = getColor(R.color.code_2E3156, LocalContext.current),
                shape = RoundedCornerShape(4.dp)
            ),
        elevation = 4.dp,
    ) {
        LazyColumn {
            item {
                FilterTypeTittleView()
            }
            item {
                if (isFilterTypeViewVisible.value) {
                    FilterDataTypeView(filterTypeList, modifier = Modifier.height(300.dp))
                } else FilterDataTypeView(filterTypeList, modifier = Modifier.height(0.dp))

            }
        }
    }
}

@Composable
fun FilterDataTypeView(
    filterTypeList: MutableState<ArrayList<FilterPokeTypeModel>>,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Divider(
            color = getColor(R.color.code_2E3156_alpha, LocalContext.current),
            modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
                .padding(start = 14.dp, end = 14.dp, bottom = 20.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .height(300.dp)
                .width(500.dp)
        ) {
            items(filterTypeList.value.size) { index ->
                val isChecked = remember { mutableStateOf(filterTypeList.value[index].isSelected) }
                Row {
                    Checkbox(
                        checked = isChecked.value,
                        onCheckedChange = {
                            isChecked.value = it
                            filterTypeList.value[index].isSelected = it
                            isCheckChange.value = true
                            if (it) {
                                selectedFilterList.add(filterTypeList.value[index])
                            } else {
                                selectedFilterList.remove(filterTypeList.value[index])
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterVertically),
                        colors = CheckboxDefaults.colors(
                            getColor(
                                R.color.code_2E3156,
                                LocalContext.current
                            )
                        )
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = filterTypeList.value[index].pokeName,
                        fontSize = 14.sp,
                        color = getColor(R.color.code_2E3156, LocalContext.current)
                    )
                }
            }
        }
    }
}


@Composable
fun FilterTypeTittleView() {
    val selectedFilterStr = remember {
        mutableStateOf("")
    }
    if (isCheckChange.value) {
        isCheckChange.value = false
        selectedFilterStr.value = getSelectedFilter()
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.CenterVertically)
                .weight(1f),
            text = stringResource(R.string.filter_type),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = getColor(R.color.code_2E3156, LocalContext.current)
        )

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .weight(1f)
        ) {
            Divider(
                color = getColor(R.color.code_2E3156_alpha, LocalContext.current),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .padding(top = 8.dp, bottom = 8.dp)
            )
            Text(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .clickable { isFilterTypeViewVisible.value = !isFilterTypeViewVisible.value },
                text = selectedFilterStr.value,
                fontSize = 14.sp,
                //fontWeight = FontWeight.Bold,
                color = getColor(R.color.code_2E3156, LocalContext.current)
            )
        }
        Image(
            painterResource(id = if (isFilterTypeViewVisible.value) R.drawable.ic_minus else R.drawable.plus),
            contentDescription = "filter expand",
            modifier = Modifier
                .padding(15.dp)
                .weight(1f)
                .align(Alignment.CenterVertically)
                .clickable { isFilterTypeViewVisible.value = !isFilterTypeViewVisible.value }
        )
    }
}

@Composable
fun getSelectedFilter(): String {
    var strCount = 0
    var selectedValue = ""
    for (item: FilterPokeTypeModel in selectedFilterList) {
        if (item.isSelected && selectedValue.isEmpty()) {
            selectedValue = item.pokeName
        } else if (item.isSelected) {
            strCount++
        }
    }

    return getAnnotatedStr(selectedValue, strCount)

}

fun getAnnotatedStr(selectedValue: String, strCount: Int): String {
    val annotatedString = buildAnnotatedString {
        append(selectedValue)
        withStyle(
            style = SpanStyle(
                Color.Blue,
                fontWeight = FontWeight.Bold
            )
        ) {
            if (strCount > 0) {
                append(" + $strCount more")
            }
        }
    }
    return annotatedString.text
}

