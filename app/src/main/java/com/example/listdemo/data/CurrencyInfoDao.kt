package com.example.listdemo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrencyInfoDao {

    @Query("SELECT * FROM currencyInfo")
    suspend fun getAll(): List<CurrencyInfo>

    @Insert
    suspend fun insertAll(currencyInfo: List<CurrencyInfo>)

    @Delete
    suspend fun delete(currencyInfo: CurrencyInfo)

    @Query("DELETE FROM currencyInfo")
    suspend fun deleteAll()

    @Query("SELECT * FROM currencyInfo ORDER BY name ASC")
    fun getASCSorting(): List<CurrencyInfo>

    @Query("SELECT * FROM currencyInfo ORDER BY name DESC")
    fun getDESCSorting(): List<CurrencyInfo>
}