package com.example.listdemo.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listdemo.data.CurrencyInfo
import com.example.listdemo.data.CurrencyInfoDao
import com.example.listdemo.data.MockDataHelper
import com.example.listdemo.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val currencyInfoDao: CurrencyInfoDao,
    private val mockDataHelper: MockDataHelper
) : ViewModel() {

    var currencyInfoItemViewModels = MediatorLiveData<List<CurrencyInfoItemViewModel>>()
    var sortingFlag = false

    fun setCurrencyList(list: ArrayList<CurrencyInfo>?) {
        currencyInfoItemViewModels.postValue(mapCurrencyData(list))
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun mapCurrencyData(list: ArrayList<CurrencyInfo>?) : List<CurrencyInfoItemViewModel>{

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
        viewModelScope.launch(dispatcher) {
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
        viewModelScope.launch(dispatcher) {
            val resultFromDb = if(sortingFlag){
                sortingFlag = false
                currencyInfoDao.getASCSorting()
            } else {
                sortingFlag = true
                currencyInfoDao.getDESCSorting()
            }

            val result = ArrayList<CurrencyInfo>()
            for(item in resultFromDb){
                result.add(item)
            }

            currencyInfoItemViewModels.postValue(mapCurrencyData(result))
        }
    }

}