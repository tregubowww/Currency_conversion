package ru.myuniquenickname.myapplication.presentation.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.myuniquenickname.currencyconversion.data.*
import ru.myuniquenickname.currencyconversion.domain.GetCurrencyInteractor
import ru.myuniquenickname.currencyconversion.presentation.CurrencyViewModel
import ru.myuniquenickname.myapplication.data.db.provideCurrencyDao
import ru.myuniquenickname.myapplication.data.db.provideDatabase

val appModule = module {
    single { GetCurrencyInteractor(get()) }
}

val viewModel = module {
    viewModel { CurrencyViewModel(get()) }
}

val apiModule = module {
    single { provideJson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideMoviesApi(get()) }
}

val repositoryModule = module {
    single { CurrencyRepository(get(), get()) }
}

val databaseModule = module {

    single { provideDatabase(androidApplication()) }
    single { provideCurrencyDao(get()) }
}
