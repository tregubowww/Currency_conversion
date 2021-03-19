package ru.myuniquenickname.currencyconversion.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.myuniquenickname.currencyconversion.domain.Currency

class CurrencyRepository(private val currencyApi: CurrencyApi, private val currencyDao: CurrencyDao) {

    suspend fun getCurrencyListFromDb(): List<Currency> = withContext(Dispatchers.IO) {
        currencyDao.getListCurrency()
    }
    suspend fun loadCurrencyList(): List<Currency> = withContext(Dispatchers.IO) {
        val listCurrency = parseCurrency(
            currencyApi.getCurrencyMap().valute
        )
        currencyDao.addCurrency(listCurrency)
        listCurrency
    }

    private fun parseCurrency(
        currencyDto: Map<String, ResultCurrencyDto>,

    ): List<Currency> {

        return currencyDto.map { currencyDto ->
            Currency(
                id = currencyDto.value.id,
                numCode = currencyDto.value.numCode,
                charCode = currencyDto.key,
                nominal = currencyDto.value.nominal,
                name = currencyDto.value.name,
                value = currencyDto.value.value,
                previous = currencyDto.value.previous
            )
        }
    }
}
