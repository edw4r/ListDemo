package com.example.listdemo.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrencyInfo(
    val id : String,
    val name : String,
    val symbol : String
) : Parcelable
