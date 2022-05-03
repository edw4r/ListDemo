package com.example.listdemo.di

import android.content.Context
import androidx.room.Room
import com.example.listdemo.data.CurrencyDatabase
import com.example.listdemo.data.CurrencyInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): CurrencyDatabase {
        return Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java,
            "CurrencyDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyInfoDao(currencyDatabase: CurrencyDatabase): CurrencyInfoDao {
        return currencyDatabase.currencyInfoDao()
    }

}