package com.example.listdemo.viewmodel

import android.os.Parcelable
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.listdemo.data.CurrencyInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor() : ViewModel() {

    var currencyInfoItemViewModels = MediatorLiveData<List<CurrencyInfoItemViewModel>>()

    fun setCurrencyList(list: ArrayList<CurrencyInfo>?) {

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

        currencyInfoItemViewModels.postValue(result)
    }

}