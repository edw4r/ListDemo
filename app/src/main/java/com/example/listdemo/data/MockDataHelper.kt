package com.example.listdemo.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class MockDataHelper @Inject constructor( @ApplicationContext val context: Context ) {

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getCurrencyMockData(): List<CurrencyInfo> {
        val jsonFileString = getJsonDataFromAsset(context, "sample_currency_data.json")
        val gson = Gson()
        val listCurrencyInfoType = object : TypeToken<List<CurrencyInfo>>() {}.type
        return gson.fromJson(jsonFileString, listCurrencyInfoType)
    }

}