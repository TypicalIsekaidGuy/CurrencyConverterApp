package com.example.currencyconverterapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverterapp.R
import androidx.navigation.NavController
import com.example.currencyconverterapp.MainViewModel
import com.example.currencyconverterapp.ui.theme.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, currencyFlow: StateFlow<List<Currency>>, viewModel: MainViewModel) {
    val tag = "MAIN_SCREEN"
    val currencyList by currencyFlow.collectAsState()
/*    val currencyList by viewModel.currencies.collectAsState()*/
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // Coroutine scope to use for opening the drawer from an event
    val coroutineScope = rememberCoroutineScope()

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()



    // Thoose Drawers are designed to peek a bit from the side where they supposed to be opened, in order to get rif of such behaviour another screen is needed
    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            Box(modifier = Modifier.background(BGWhite)){

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 48.dp)
                ) {
                    Text(
                        text = "Filters",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Column {
                        val radioOptions = listOf("Rate > 1", "Rate < 1")
                        var selectedOption by remember { mutableStateOf("")}
                        radioOptions.forEach{option->
                            RadioButtonOption(text = option, selected = selectedOption==option) {
                                if(selectedOption==option){
                                    selectedOption = ""
                                    viewModel.filterDefault()
                                }
                                else {
                                    selectedOption = option
                                    viewModel.filterRate(option=="Rate > 1")
                                }
                            }
                        }
                    }
                }
            }

        },
        content =        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                TopBar(
                    onDrawerIconClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                    searchText,
                    { viewModel.onSearchTextChange(searchText) })

                val list = listOf(
                    SortElement("Name", Sort.NAME, { viewModel.sortByName() }) { viewModel.sortByDefault() },
                    SortElement("Price", Sort.PRICE, { viewModel.sortByPrice() }) { viewModel.sortByDefault() },
                    SortElement("Trend", Sort.TREND, {  viewModel.sortByTrend() }) {viewModel.sortByDefault()  },
                    SortElement("Trend %", Sort.TREND_PERCENTAGE, { viewModel.sortByPercentage() }) { viewModel.sortByDefault() }
                )
                SortBar(Modifier.align(Alignment.CenterHorizontally), list)
                CurrencySection(currencyList, navController)
            }
        }

    )

}

@Composable
fun RadioButtonOption(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = ButtonPressedGray)
        )

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,       )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onDrawerIconClick: () ->  Unit, searchText: String, onValueChange: ()-> Unit){
    var text by remember{ mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_menu_24),
            contentDescription = "Icon 1",
            modifier = Modifier.clickable { onDrawerIconClick() }
        )
        TextField(value = searchText, onValueChange ={text = it
            onValueChange}  )
        Icon(
            painter = painterResource(id = R.drawable.baseline_search_24),
            contentDescription = "Icon 2",
            modifier = Modifier.clickable {  }
        )
    }
}
@Composable
fun SortBar(modifier: Modifier, sortList: List<SortElement>){
    val pickedId = remember{
        mutableStateOf(-1)
    }
    Column(modifier = modifier.fillMaxWidth()) {

        Text(
            text = "Currencies",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Sort by",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )

        LazyRow(modifier = Modifier.padding(top = 16.dp)){
            items(sortList.size){item->
                SortItem(sortItem = sortList[item], pickedId = pickedId)
            }
        }
    }
}
@Composable
fun SortItem(sortItem: SortElement, pickedId: MutableState<Int>){
    Box(modifier = Modifier.padding(start = 12.dp)){
        Box(modifier = Modifier
            .clip(CircleShape)
            .background(if (pickedId.value == sortItem.sortBy.ordinal) ButtonPressedGray else ButtonNotPressedGray)
            .clickable {
                if (pickedId.value == sortItem.sortBy.ordinal) {
                    pickedId.value = -1
                    sortItem.onClickDefault()
                } else {
                    pickedId.value = sortItem.sortBy.ordinal
                    sortItem.onClick()
                }
            }
            .padding(horizontal = 12.dp, vertical = 4.dp)){
            Text(fontSize = 16.sp, color = LightBlack, text = sortItem.name)
        }
    }
}

@Composable
fun CurrencySection(currencies: List<Currency>, navController: NavController){
    LazyColumn(modifier = Modifier
        .padding(top = 12.dp)
        .padding(horizontal = 8.dp)
        .fillMaxWidth()){
        items(currencies.size){item->
            CurrencyItem(currencyItem = currencies[item]) {  navController.navigate(Screen.CalculatorScreen.withArgs(currencies[item].name,"${currencies[item].price}")) }
        }
    }
}
@Composable
fun CurrencyItem(currencyItem: Currency, navigateToScreen: () -> Unit) {
    Column (modifier = Modifier
        .padding(bottom = 16.dp)){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { navigateToScreen() }) {
            Image(
                painter = painterResource(id = currencyItem.image),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = currencyItem.name, fontSize = 16.sp, color = LightBlack)
                        Text(text = (currencyItem.anotherPrice).toString(), fontSize = 16.sp, color = LighterBlack)
                    }
                    Column (
                        verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = (currencyItem.trendProcentage).toString() + "%",
                            fontSize = 16.sp,
                            color = if (currencyItem.trend) GreenTrend else RedTrend,
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = currencyItem.price.toString(),
                            fontSize = 16.sp,
                            color = LighterBlack,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .size(width = 316.dp, height = 2.dp)
                .height(2.dp)
                .background(color = VeryGray)
        )
    }
}