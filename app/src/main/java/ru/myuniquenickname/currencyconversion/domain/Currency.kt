package ru.myuniquenickname.currencyconversion.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "currency"
)
data class Currency(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "num_code")
    val numCode: String,

    @ColumnInfo(name = "char_code")
    val charCode: String,

    val nominal: Long,
    val name: String,
    val value: Double,
    val previous: Double
)
