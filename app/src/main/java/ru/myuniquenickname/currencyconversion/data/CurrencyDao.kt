package ru.myuniquenickname.currencyconversion.data

import androidx.room.*
import ru.myuniquenickname.currencyconversion.domain.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT*  FROM currency")
    fun getListCurrency(): List<Currency>

    @Insert
    (onConflict = OnConflictStrategy.REPLACE)
    fun addCurrency(currency: List<Currency>)
}
