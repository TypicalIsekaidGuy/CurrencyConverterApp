package com.example.currencyconverterapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.currencyconverterapp.MainViewModel
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.theme.BGWhite
import com.example.currencyconverterapp.ui.theme.ButtonPressedGray
import com.example.currencyconverterapp.ui.theme.LightBlack
import com.example.currencyconverterapp.ui.theme.Transparent
import com.example.currencyconverterapp.ui.theme.VeryGray

@Composable
fun CalculatorScreen(navController: NavController, viewModel: MainViewModel, name: String, price: Float){
    var totalSum = remember { mutableStateOf(0F) }
    var amount = remember { mutableStateOf(0F) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SearchTopBar(name)
        PriceBar(name,price, Modifier.align(Alignment.CenterHorizontally))
        InputFeilds(name, totalSum, price, amount)
        NumPadSection(Modifier.align(Alignment.CenterHorizontally), totalSum, viewModel )
        BuyButton(name, totalSum.value) {navController.popBackStack()}

    }
}


@Composable
fun SearchTopBar(name: String){

    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Buy $name", fontSize = 24.sp, color = LightBlack, textAlign = TextAlign.Center)
        Box(modifier = Modifier
            .clip(CircleShape)
            .background(VeryGray)
            .align(Alignment.CenterVertically)
            .padding(8.dp)){
            Icon(
                painter = painterResource(id = R.drawable.baseline_exit_24),
                contentDescription = "Icon 2",
                modifier = Modifier.clickable {  }
            )
        }
    }
}
@Composable
fun PriceBar(name: String, price: Float, modifier: Modifier){
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(top = 32.dp)){
        Text(text = "1 $name = $price USD", fontSize = 16.sp, color = LightBlack, textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .align(
                Alignment.Center
            ))
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFeilds(name: String, totalSum: MutableState<Float>, price: Float, amount: MutableState<Float>) {
    Column {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 32.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = VeryGray)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)) {
                    Text(
                        text = "Receive",
                        fontSize = 12.sp,
                        color = LightBlack,
                        maxLines = 1
                    )
                }
                TextField(
                    value = amount.value.toString(),
                    onValueChange = {
                        amount.value = it.toFloat()
                        totalSum.value = amount.value*price  },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Transparent
                    ),
                    textStyle = TextStyle(textAlign = TextAlign.End),
                    modifier = Modifier.weight(2f) // Set the weight of the TextField to 2
                )
                Box(modifier = Modifier
                    .weight(0.4f)
                    .align(Alignment.CenterVertically)) {
                    Text(
                        text = "$name",
                        fontSize = 12.sp,
                        color = LightBlack,
                        maxLines = 1
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = ButtonPressedGray)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)) {
                    Text(
                        text = "Buy for",
                        fontSize = 12.sp,
                        color = LightBlack,
                        maxLines = 1
                    )
                }
                TextField(
                    value = totalSum.value.toString(),
                    onValueChange = {
                        totalSum.value = it.toFloat()
                        amount.value = totalSum.value/price
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Transparent
                    ),
                    textStyle = TextStyle(textAlign = TextAlign.End),
                    modifier = Modifier.weight(2f) // Set the weight of the TextField to 2
                )
                Box(modifier = Modifier
                    .weight(0.4f)
                    .align(Alignment.CenterVertically)) {
                    Text(
                        text = "USD",
                        fontSize = 12.sp,
                        color = LightBlack,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
@Composable
fun NumPadSection(modifier: Modifier, amount: MutableState<Float>, viewModel: MainViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .size(256.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(9) {
            NumPadItem(i = it + 1) {
                viewModel.insertDigit(amount, it + 1)
            }
        }
        item {
            PointItem()
        }
        item {
            NumPadItem(i = 0) {
                viewModel.insertDigit(amount, 0)
            }
        }
        item {
            DeleteItem {
                viewModel.deleteDigit(amount)
            }
        }
    }
}

@Composable
fun NumPadItem(i: Int, insertDigit: (digit: Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clickable {
                // Call the insertDigit function with the digit value when the item is clicked
                insertDigit(i)
            }
    ) {
        Text(
            text = "$i",
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun PointItem(){

    Box(modifier = Modifier
        .size(48.dp)
        .clickable { }){
        Text(text = ".", textAlign = TextAlign.Center, fontSize = 32.sp, modifier = Modifier.align(
            Alignment.BottomCenter))
    }
}
@Composable
fun DeleteItem(deleteDigit: ()-> Unit){

    Box(modifier = Modifier
        .size(48.dp)
        .clickable { deleteDigit }){
        Icon(
            painter = painterResource(id = R.drawable.baseline_exit_24),
            contentDescription = "Icon 2",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
@Composable
fun BuyButton(name: String, amount: Float, onClick: ()-> Boolean){
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).size(64.dp), verticalArrangement = Arrangement.SpaceEvenly) {
        Text(text = "This service doesnt include any fees", fontSize = 16.sp, color = VeryGray, textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .align(
                Alignment.CenterHorizontally
            ))
        Box(modifier = Modifier.background(LightBlack).clip(RoundedCornerShape(32.dp)).clickable { onClick }){
            Text(text = "Buy $name for $amount USD", fontSize = 18.sp, color = BGWhite, textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxWidth()
                .align(
                    Alignment.Center
                )
            )
        }
    }
}