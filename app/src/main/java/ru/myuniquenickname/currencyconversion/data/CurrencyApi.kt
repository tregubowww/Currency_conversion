package ru.myuniquenickname.currencyconversion.data

import retrofit2.http.GET

interface CurrencyApi {
    @GET("daily_json.js")
    suspend fun getCurrencyMap(): CurrencyDto
}
