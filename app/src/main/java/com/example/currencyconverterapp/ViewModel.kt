package com.example.currencyconverterapp

import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.ui.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import retrofit2.HttpException
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.absoluteValue


class MainViewModel(): ViewModel(),NumPadInterface  {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val tag = "MAIN_VIEWMODEL"
    private val _data = MutableStateFlow<List<Currency>>(emptyList())
    val currentCurrencyList: MutableList<Currency> = mutableListOf()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    val data: StateFlow<List<Currency>> get() = _data.asStateFlow()

    init {
        fetchDataFromNetwork()
    }

    fun onSearchTextChange(text: String) {
        Log.d(tag, text)
        _searchText.value = text
    }
    fun onSearchChange(text: String){
        _searchText.value= text
        val filteredData = if (_searchText.value.isBlank()) {
            // If the search text is empty, show all data
            currentCurrencyList.toList()
        } else {
            currentCurrencyList.filter { currency ->
                currency.name.contains(_searchText.value, ignoreCase = true)
            }
        }
        _data.value = filteredData
    }
    fun getFirstFiveElements(map: Map<String, Float>): List<Pair<String, Float>> {
        return map.toList().take(2)
    }

    fun splitMap(map: Map<String, Float>): Pair<List<Pair<String, Float>>, List<Pair<String, Float>>> {
        val entriesList = map.entries.toList()
        val firstFive = entriesList.take(2).map { it.key to it.value }
        val remaining = entriesList.drop(2).map { it.key to it.value }
        return firstFive to remaining
    }
    //Redact this function
    override fun insertDigit(amount: MutableState<Float>, digit: Int): Boolean {
        val amountAsString = amount.value.toString()

        var trimmedAmount = ""
        if(amountAsString.startsWith("0.")){
            if(digit!=0) {
                trimmedAmount = if (amountAsString == "0.0") {
                    "0.$digit"
                }else{
                    "$digit.${amountAsString.drop(2)}"
                }
                amount.value = trimmedAmount.toFloat()
                return true
            }
        }


        // Append the digit to the trimmed amount string
        val newAmountString = "$digit${amount.value}"

        if(newAmountString.length<10){
            amount.value = newAmountString.toFloat()
            return true
        }
        return false
    }
    override fun deleteDigit( amount: MutableState<Float>):Boolean {
        if (amount.value == 0.0F) {
            // If the amount is already 0.0F, do nothing
            return false
        }

        val amountAsString = amount.value.toString()

        var trimmedAmount = ""
        if(amountAsString.startsWith("0.")){
                trimmedAmount = if (amountAsString == "0.0") {
                    return false
                }else{
                    "0.0"
                }
                amount.value = trimmedAmount.toFloat()
                return true

        }
        Log.d("SSSsssssssss", amount.value.toString())


        amount.value = amountAsString.drop(1).toFloat()
        return true
    }




    private fun getTrend(json: String?):Float?{

        try {
            val jsonObject = JSONObject(json)
            val timeSeriesData = jsonObject.getJSONObject("Time Series (Daily)")

            // Get the latest date
            val latestDate = timeSeriesData.keys().next()

            // Get the date 30 days before the latest date
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val latestDateObj = sdf.parse(latestDate)
            val thirtyDaysAgoDateObj = Calendar.getInstance()
            thirtyDaysAgoDateObj.time = latestDateObj
            thirtyDaysAgoDateObj.add(Calendar.DAY_OF_MONTH, -30)
            val thirtyDaysAgoDate = sdf.format(thirtyDaysAgoDateObj.time)

            // Get the rates for the latest date and the date 30 days ago
            val latestRateObj = timeSeriesData.getJSONObject(latestDate)
            val latestRate = latestRateObj.getString("4. close").toFloatOrNull()

            val thirtyDaysAgoRateObj = timeSeriesData.getJSONObject(thirtyDaysAgoDate)
            val thirtyDaysAgoRate = thirtyDaysAgoRateObj.getString("4. close").toFloatOrNull()

            // Check if the rates are available and not null
            if (latestRate != null && thirtyDaysAgoRate != null) {
                // Perform calculations with the rates as needed
                val percentageChange = ((latestRate - thirtyDaysAgoRate) / thirtyDaysAgoRate) * 100f

                // Create a DecimalFormat with a pattern that includes 6 digits after the decimal point
                val decimalFormat = DecimalFormat("#.##")

                Log.d("SSSSSS","$percentageChange")
                // Format the number using the DecimalFormat
                val formattedNumber = decimalFormat.format(percentageChange).toFloat()
                return formattedNumber
            } else {
                // Handle the case when the rates are not available or parsing fails
                println("SSSSS Rates data not available or parsing failed.")
            }
        } catch (e: Exception) {
            // Handle any exceptions that might occur during parsing
            println("SSSSS Error parsing JSON: ${e.message}")
        }
        return null
    }


    private fun fetchDataFromNetwork() {

        viewModelScope.launch {
            try {
                // Define the base URL and endpoint
                val baseUrl = "https://www.alphavantage.co/query"
                val endpoint = "function=TIME_SERIES_DAILY"

                // Replace with your actual API key
                val apiKey = RetrofitClient.ALPHA_API_KEY

                // Create an instance of OkHttpClient
                val client = OkHttpClient()
                val response = RetrofitClient.exchangeRatesApiService.getLatestExchangeRates(RetrofitClient.ER_API_KEY)
                var exchangeRates:  Map<String, Float> = mapOf()
                if (response.success) {
                    exchangeRates = response.rates

                }
                val (alphaApiList, exchangeApiList) = splitMap(exchangeRates)
                val alpha = getFirstFiveElements(exchangeRates)
                for (currency in alpha){
                    val symbol = currency.first
 /*                   for (currency in alphaApiList) {
                        val newCurrency = fetchCurrencyData(currency.first,baseUrl, endpoint,apiKey,client,exchangeRates  )
                        if (newCurrency != null) {
                            // Get the current list of currencies
                            val currentCurrencies = _data.value.toMutableList()

                            // Add the new currency to the list
                            currentCurrencies.add(newCurrency)

                            // Update the _data MutableStateFlow with the updated list of currencies
                            _data.value = currentCurrencies.toList()
                        }
                    }*/
                    // Create the complete API URL with query parameters
                    val apiUrl = "$baseUrl?$endpoint&symbol=$symbol&apikey=$apiKey"

                    // Build the request
                    val request = Request.Builder()
                        .url(apiUrl)
                        .build()

                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            // Handle network-related exceptions
                            println("Network error: ${e.message}")
                        }

                        override fun onResponse(call: Call, response: Response) {
                            // Check if the request was successful
                            if (response.isSuccessful) {
                                // Read the response body as a string
                                val jsonResponse = response.body()?.string()
                                Log.d("SSSSSS",currency.first)
                                val trendPercentage = getTrend(jsonResponse)
                                if(trendPercentage!=null){
                                    Log.d("SSSSSS",currency.first)
                                    val rate = exchangeRates[symbol] ?: 0f

                                    val newCurrency = Currency(symbol,R.drawable.dollar,rate, trendPercentage>0, trendPercentage, trendPercentage.absoluteValue*rate)
                                    println("${newCurrency.trendProcentage}")
                                    // Get the current list of currencies
                                    currentCurrencyList.add(newCurrency)
                                }
                            } else {
                                // Handle unsuccessful response
                                println("Request failed: ${response.code()} - ${response.message()}")
                            }
                        }})
                }
                for (currency in exchangeApiList){
                    val newCurrency = Currency(currency.first,R.drawable.dollar,currency.second, true, currency.second/1000, currency.second/10)

                    // Update the _data MutableStateFlow with the updated list of currencies
                    currentCurrencyList.add(newCurrency)
                }
                filterDefault()
                _isLoading.value = false
                // After fetching, filter the data based on the search text
/*                val filteredData = if (_searchText.value.isBlank()) {
                    // If the search text is empty, show all data
                    currentCurrencyList.toList()
                } else {
                    currentCurrencyList.filter { currency ->
                        currency.name.contains(_searchText.value, ignoreCase = true)
                    }
                }
                _data.value = filteredData*/

            } catch (e: HttpException) {
            }catch (e: Exception) {
                Log.v("SSS", "$e")
            }
        }
    }
    fun filterRate(isBiggerOrLess: Boolean){
        var tempList = currentCurrencyList
        if(isBiggerOrLess)
            _data.value = tempList.filter { currency-> currency.price>=1 }
        else
            _data.value = tempList.filter { currency-> currency.price<1 }
    }
    fun filterDefault(){
        _data.value = currentCurrencyList
    }
    fun sortByName(){

        var tempList = currentCurrencyList
        _data.value = tempList.sortedBy { it.name }
    }
    fun sortByPrice(){

        var tempList = currentCurrencyList
        _data.value = tempList.sortedBy { it.price }
    }

    fun sortByTrend(){
        var tempList = currentCurrencyList
        _data.value = tempList.sortedWith(compareByDescending<Currency> { it.trend }.thenBy { it.price })
    }

    fun sortByPercentage(){
        var tempList = currentCurrencyList
        _data.value = tempList.sortedBy { it.trendProcentage }
    }

    fun sortByDefault(){
        // Assuming you have a separate variable to hold the original unsorted list
        _data.value = currentCurrencyList
    }
}
