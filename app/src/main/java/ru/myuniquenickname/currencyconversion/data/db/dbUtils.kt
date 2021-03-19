package ru.myuniquenickname.myapplication.data.db

import android.app.Application
import androidx.room.Room
import ru.myuniquenickname.currencyconversion.data.CurrencyDao
import ru.myuniquenickname.myapplication.data.CurrencyDatabase

fun provideDatabase(application: Application): CurrencyDatabase {
    return Room.databaseBuilder(application, CurrencyDatabase::class.java, CurrencyDatabase.NAME_DB)
        .fallbackToDestructiveMigration()
        .build()
}
fun provideCurrencyDao(database: CurrencyDatabase): CurrencyDao {
    return database.currencyDao
}
