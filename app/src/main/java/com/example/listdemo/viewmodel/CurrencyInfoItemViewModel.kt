package com.example.listdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CurrencyInfoItemViewModel() {
    var id = MutableLiveData<String>("")
    var name = MutableLiveData<String>("")
    var symbol = MutableLiveData<String>("")
    var initialChar = MutableLiveData<String>("")
}