package com.example.listdemo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyInfoDao(): CurrencyInfoDao

}