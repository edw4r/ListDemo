package com.example.listdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.listdemo.data.CurrencyInfo

class CurrencyInfoItemViewModel() {
    var id = MutableLiveData<String>("")
    var name = MutableLiveData<String>("")
    var symbol = MutableLiveData<String>("")
    var initialChar = MutableLiveData<String>("")

    override fun equals(other: Any?): Boolean {
        return other.hashCode() == this.hashCode()
    }

    override fun hashCode(): Int {
        var result = id.value.hashCode()
        result = 31 * result + name.value.hashCode()
        result = 31 * result + symbol.value.hashCode()
        result = 31 * result + initialChar.value.hashCode()
        return result
    }
}