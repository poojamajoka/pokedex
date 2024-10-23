package com.pokemon.pokedex.ui.home.filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.pokemon.pokedex.R
import com.pokemon.pokedex.data.model.GenderData
import com.pokemon.pokedex.data.model.PokeGenderMasterData
import com.pokemon.pokedex.util.GradientColor
import com.pokemon.pokedex.util.Utility.Companion.toUpperFirstChar

private var isGenderViewVisible = mutableStateOf(false)
private var isCheckChange = mutableStateOf(false)
private val selectedFilterList = ArrayList<GenderData>()

@Composable
fun FilterGenderView(genderLiveData: LiveData<PokeGenderMasterData>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .border(
                1.dp,
                color = GradientColor.getColor(R.color.code_2E3156, LocalContext.current),
                shape = RoundedCornerShape(4.dp)

            ),
        elevation = 4.dp,
    ) {
        LazyColumn {
            item {
                FilterGenderTittleView()
            }
            item {
                if (isGenderViewVisible.value) {
                    FilterGenderDataView(genderLiveData, modifier = Modifier.height(120.dp))
                } else FilterGenderDataView(genderLiveData, modifier = Modifier.height(0.dp))
            }
        }
    }
}

@Composable
fun FilterGenderDataView(genderList: LiveData<PokeGenderMasterData>, modifier: Modifier) {
    Column(modifier = modifier) {
        Divider(
            color = GradientColor.getColor(R.color.code_2E3156_alpha, LocalContext.current),
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
            genderList.value?.results?.let {
                items(it.size) { index ->
                    genderList.value?.results?.let { list ->
                        val isChecked =
                            remember { mutableStateOf(list[index].isSelected) }
                        Row {
                            Checkbox(
                                checked = isChecked.value,
                                onCheckedChange = {checkChange->
                                    isChecked.value = checkChange
                                    list[index].isSelected = checkChange
                                    isCheckChange.value = true
                                    if (checkChange) {
                                        selectedFilterList.add(list[index])
                                    } else {
                                        selectedFilterList.remove(list[index])
                                    }
                                },
                                modifier = Modifier.align(Alignment.CenterVertically),
                                colors = CheckboxDefaults.colors(
                                    GradientColor.getColor(
                                        R.color.code_2E3156,
                                        LocalContext.current
                                    )
                                )
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                text = list[index].name.toUpperFirstChar(),
                                fontSize = 14.sp,
                                color = GradientColor.getColor(
                                    R.color.code_2E3156,
                                    LocalContext.current
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
fun FilterGenderTittleView() {
    val selectedFilterStr = remember {
        mutableStateOf("")
    }
    if (isCheckChange.value) {
        isCheckChange.value = false
        selectedFilterStr.value = getSelectedGenderFilter()
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.CenterVertically)
                .weight(1f),
            text = stringResource(R.string.filter_gender),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = GradientColor.getColor(R.color.code_2E3156, LocalContext.current)
        )

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .weight(1f)
        ) {
            Divider(
                color = GradientColor.getColor(R.color.code_2E3156_alpha, LocalContext.current),
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
                    .clickable { isGenderViewVisible.value = !isGenderViewVisible.value },
                text = selectedFilterStr.value,
                fontSize = 14.sp,
                color = GradientColor.getColor(R.color.code_2E3156, LocalContext.current)
            )
        }
        Image(
            painterResource(id = if (isGenderViewVisible.value) R.drawable.ic_minus else R.drawable.plus),
            contentDescription = "filter expand",
            modifier = Modifier
                .padding(15.dp)
                .weight(1f)
                .align(Alignment.CenterVertically)
                .clickable {
                    isGenderViewVisible.value = !isGenderViewVisible.value
                }
        )
    }
}

@Composable
fun getSelectedGenderFilter(): String {
    var strCount = 0
    var selectedValue = ""
    for (item: GenderData in selectedFilterList) {
        if (item.isSelected && selectedValue.isEmpty()) {
            selectedValue = item.name.toUpperFirstChar()
        } else if (item.isSelected) {
            strCount++
        }
    }

    return getAnnotatedStr(selectedValue, strCount)

}