package com.example.listdemo.viewmodel

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listdemo.data.CurrencyInfo
import com.example.listdemo.data.CurrencyInfoDao
import com.example.listdemo.data.MockDataHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    val currencyInfoDao: CurrencyInfoDao,
    val mockDataHelper: MockDataHelper
) : ViewModel() {

    var currencyInfoItemViewModels = MediatorLiveData<List<CurrencyInfoItemViewModel>>()

    fun setCurrencyList(list: ArrayList<CurrencyInfo>?) {
        currencyInfoItemViewModels.postValue(mapCurrencyData(list))
    }

    private fun mapCurrencyData(list: ArrayList<CurrencyInfo>?) : List<CurrencyInfoItemViewModel>{

        val result = ArrayList<CurrencyInfoItemViewModel>()

        if(list != null){
            for (item in list) {
                val currentInfoItem = CurrencyInfoItemViewModel().apply {
                    this.id.value = item.id
                    this.name.value = item.name
                    this.symbol.value = item.symbol
                    if(item.name.isNotEmpty()){
                        this.initialChar.value = item.name.first().toString()
                    }
                }
                result.add(currentInfoItem)
            }
        }

        return result
    }

    fun onLoadButtonClick() {
        viewModelScope.launch {
            val data = mockDataHelper.getCurrencyMockData()
            if(currencyInfoDao.getAll().isEmpty()){
                currencyInfoDao.insertAll(data)
            }

            val result = ArrayList<CurrencyInfo>()
            for(item in currencyInfoDao.getAll()){
                result.add(item)
            }

            currencyInfoItemViewModels.postValue(mapCurrencyData(result))
        }

    }

    fun onSortButtonClick() {

    }

}