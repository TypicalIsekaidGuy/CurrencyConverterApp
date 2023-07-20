package com.example.currencyconverterapp.ui

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.theme.BGWhite
import com.example.currencyconverterapp.ui.theme.ButtonNotPressedGray
import com.example.currencyconverterapp.ui.theme.LightBlack
import com.example.currencyconverterapp.ui.theme.Transparent
import com.example.currencyconverterapp.ui.theme.VeryGray
import com.example.currencyconverterapp.ui.theme.ButtonPressedGray

@Composable
fun SearchScreen(navController: NavController, name: String, price: Float,){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SearchTopBar(name)
        PriceBar(name,price, Modifier.align(Alignment.CenterHorizontally))
        InputFeilds(name)
        NumPadSection(Modifier.align(Alignment.CenterHorizontally))
        BuyButton(name, price)

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
fun InputFeilds(name: String) {
    var reciveValue by remember {
        mutableStateOf("")
    }

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
                    value = reciveValue,
                    onValueChange = { reciveValue = it },
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
                        text = "Receive",
                        fontSize = 12.sp,
                        color = LightBlack,
                        maxLines = 1
                    )
                }
                TextField(
                    value = reciveValue,
                    onValueChange = { reciveValue = it },
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
    }
}
@Composable
fun NumPadSection(modifier: Modifier){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .size(256.dp),  horizontalArrangement = Arrangement.SpaceEvenly, verticalArrangement = Arrangement.SpaceEvenly
    ){
        items(9){
            NumPadItem(i = it+1)
        }
        item{
            PointItem()
        }
        item{
            NumPadItem(i = 0)
        }
        item{
            DeleteItem()
        }
    }
}
@Composable
fun NumPadItem( i: Int){

    Box(modifier = Modifier
        .size(48.dp)
        .clickable { }){
        Text(text = "$i", textAlign = TextAlign.Center, fontSize = 32.sp, modifier = Modifier.align(Alignment.Center))
    }
}
@Composable
fun PointItem(){

    Box(modifier = Modifier
        .size(48.dp)
        .clickable { }){
        Text(text = ".", textAlign = TextAlign.Center, fontSize = 32.sp, modifier = Modifier.align(Alignment.BottomCenter))
    }
}
@Composable
fun DeleteItem(){

    Box(modifier = Modifier
        .size(48.dp)
        .clickable { }){
        Icon(
            painter = painterResource(id = R.drawable.baseline_exit_24),
            contentDescription = "Icon 2",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
@Composable
fun BuyButton(name: String, amount: Float){
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).size(128.dp), verticalArrangement = Arrangement.SpaceEvenly) {
        Text(text = "This service doesnt include any fees", fontSize = 16.sp, color = VeryGray, textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .align(
                Alignment.CenterHorizontally
            ))
        Box(modifier = Modifier.background(LightBlack).clip(RoundedCornerShape(32.dp))){
            Text(text = "Buy $amount $name", fontSize = 24.sp, color = BGWhite, textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxWidth()
                .align(
                    Alignment.Center
                )
            )
        }
    }
}
