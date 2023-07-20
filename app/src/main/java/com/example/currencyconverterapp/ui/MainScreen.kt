package com.example.currencyconverterapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverterapp.R
import androidx.navigation.NavController
import com.example.currencyconverterapp.ui.theme.*

@Composable
fun MainScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TopBar()
        val list = listOf<SortElement>(
            SortElement("Price", Sort.PRICE) ,
            SortElement("Trend", Sort.TREND) ,
            SortElement("Trend %", Sort.TREND_PERCENTAGE)
        )
        SortBar(Modifier.align(Alignment.CenterHorizontally),list)
        val list2 = listOf(
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F),
            Currency("Bitcoin","bitcoin",1000.00F,false,10.0F,100.00F)
        )
        CurrencySection(list2, navController)
    }


}

@Composable
fun TopBar(){

    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_menu_24),
            contentDescription = "Icon 1",
            modifier = Modifier.clickable {  }
        )
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
            .clickable { pickedId.value = sortItem.sortBy.ordinal }
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
            CurrencyItem(currencyItem = currencies[item]) {  navController.navigate(Screen.CalculatorScreen.route) }
        }
    }
}
@Composable
fun CurrencyItem(currencyItem: Currency, navigateToScreen: () -> Unit) {
    Column (modifier = Modifier
        .padding(bottom = 16.dp)){
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable { navigateToScreen() }) {
            Image(
                painter = painterResource(id = R.drawable.dollar),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically).clickable {  }
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
                        Text(text = currencyItem.anotherPrice.toString(), fontSize = 16.sp, color = LighterBlack)
                    }
                    Column (
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = currencyItem.trendProcentage.toString() + "%",
                            fontSize = 16.sp,
                            color = if (currencyItem.trend) GreenTrend else RedTrend,
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = currencyItem.anotherPrice.toString(),
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
                .fillMaxWidth()
                .height(2.dp)
                .background(color = VeryGray)
        )
    }
}