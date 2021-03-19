package ru.myuniquenickname.currencyconversion.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDto(
    @SerialName("Date")
    val date: String,

    @SerialName("PreviousDate")
    val previousDate: String,

    @SerialName("PreviousURL")
    val previousURL: String,

    @SerialName("Timestamp")
    val timestamp: String,

    @SerialName("Valute")
    val valute: Map<String, ResultCurrencyDto>
)

@Serializable
data class ResultCurrencyDto(
    @SerialName("ID")
    val id: String,

    @SerialName("NumCode")
    val numCode: String,

    @SerialName("CharCode")
    val charCode: String,

    @SerialName("Nominal")
    val nominal: Long,

    @SerialName("Name")
    val name: String,

    @SerialName("Value")
    val value: Double,

    @SerialName("Previous")
    val previous: Double
)
