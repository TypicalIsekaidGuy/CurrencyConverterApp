package com.example.currencyconverterapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.currencyconverterapp.ui.theme.Transparent
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun CalculatorScreen(navController: NavController, viewModel: MainViewModel, name: String, price: Float){
    var totalSum = remember { mutableStateOf(0F) }
    var amount = remember { mutableStateOf(0F) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SearchTopBar(name){ navController.popBackStack()}
        PriceBar(name,price, Modifier.align(Alignment.CenterHorizontally))
        InputFeilds(name, totalSum, price, amount)
        NumPadSection(Modifier.align(Alignment.CenterHorizontally), totalSum, amount, viewModel, { calculateTotalSum(totalSum,amount, price) } )
        BuyButton(name, totalSum.value) {navController.popBackStack()}

    }
}
fun calculateTotalSum(totalSum: MutableState<Float>, amount: MutableState<Float>, price: Float){
    totalSum.value = amount.value * price
}

@Composable
fun SearchTopBar(name: String, goBack: () -> Unit ){

    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Buy $name", fontSize = 24.sp, color = MaterialTheme.colorScheme.tertiary, textAlign = TextAlign.Center)
        Box(modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
            .align(Alignment.CenterVertically)
            .padding(8.dp)){
            Icon(
                painter = painterResource(id = R.drawable.baseline_exit_24),
                contentDescription = "Icon 2",
                modifier = Modifier.clickable { goBack() }
            )
        }
    }
}
@Composable
fun PriceBar(name: String, price: Float, modifier: Modifier){
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(top = 32.dp)){
        Text(text = "1 $name = $price USD", fontSize = 16.sp, color = MaterialTheme.colorScheme.tertiary, textAlign = TextAlign.Center, modifier = Modifier
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
                .background(color = MaterialTheme.colorScheme.secondary)
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
                        color = MaterialTheme.colorScheme.tertiary,
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
                        containerColor = Transparent,

                        textColor = MaterialTheme.colorScheme.tertiary
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
                        color = MaterialTheme.colorScheme.tertiary,
                        maxLines = 1
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = MaterialTheme.colorScheme.onPrimary)
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
                        color = MaterialTheme.colorScheme.tertiary,
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
                        containerColor = Transparent,
                        textColor = MaterialTheme.colorScheme.tertiary
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
                        color = MaterialTheme.colorScheme.tertiary,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
@Composable
fun NumPadSection(modifier: Modifier,totalSum: MutableState<Float>,  amount: MutableState<Float>, viewModel: MainViewModel, calculateTotalSum: ()-> Unit) {
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
                val isSuccesful = viewModel.insertDigit( amount, it)
                if(isSuccesful)
                    calculateTotalSum()
            }
        }
        item {
            PointItem()
        }
        item {
            NumPadItem(i = 0) {
                val isSuccesful = viewModel.insertDigit( amount, it)
                if(isSuccesful)
                    calculateTotalSum()
            }
        }
        item {
            DeleteItem {
                val isSuccesful = viewModel.deleteDigit( amount)
                if(isSuccesful)
                    calculateTotalSum()
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
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun PointItem(){

    Box(modifier = Modifier
        .size(48.dp)
        .clickable { }){
        Text(text = ".", textAlign = TextAlign.Center, fontSize = 32.sp, modifier = Modifier.align(
            Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.tertiary)
    }
}
@Composable
fun DeleteItem(deleteDigit: ()-> Unit){

    Box(modifier = Modifier
        .size(48.dp)
        .clickable { deleteDigit() }){
        Icon(
            painter = painterResource(id = R.drawable.baseline_exit_24),
            contentDescription = "Icon 2",
            modifier = Modifier.align(Alignment.Center),
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}
@Composable
fun BuyButton(name: String, amount: Float, onClick: () -> Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
            .size(64.dp)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "This service doesn't include any fees",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(horizontal = 16.dp)
                .clickable { onClick() }, // Invoke the lambda here
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Buy $name for $amount USD",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
