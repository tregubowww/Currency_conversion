package ru.myuniquenickname.currencyconversion.domain

import ru.myuniquenickname.currencyconversion.data.CurrencyRepository

class GetCurrencyInteractor(
    private val currencyRepository: CurrencyRepository
) {
    suspend fun getCurrencyList(): List<Currency> {
        return currencyRepository.getCurrencyListFromDb()
    }

    suspend fun loadCurrencyList(): List<Currency> {
        return currencyRepository.loadCurrencyList()
    }
}
