package com.example.listdemo.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class CurrencyInfo(
    @PrimaryKey val id : String,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "symbol") val symbol : String
) : Parcelable
