package ru.myuniquenickname.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.myuniquenickname.currencyconversion.data.CurrencyDao
import ru.myuniquenickname.currencyconversion.domain.Currency

@Database(
    entities = [
        Currency::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract val currencyDao: CurrencyDao

    companion object {
        const val NAME_DB = "currency_database"
    }
}
