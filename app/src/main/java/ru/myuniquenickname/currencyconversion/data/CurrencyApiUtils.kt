package ru.myuniquenickname.currencyconversion.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

fun provideJson(): Json {
    return Json {
        ignoreUnknownKeys = true
    }
}

@Suppress("EXPERIMENTAL_API_USAGE")
fun provideRetrofit(factory: Json, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(factory.asConverterFactory("application/json".toMediaType()))
        .build()
}
fun provideMoviesApi(retrofit: Retrofit): CurrencyApi {
    return retrofit.create()
}
const val BASE_URL = "https://www.cbr-xml-daily.ru/"

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}
