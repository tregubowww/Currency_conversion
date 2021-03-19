package ru.myuniquenickname.myapplication.data.work_manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.myuniquenickname.currencyconversion.data.CurrencyRepository

class CurrencyWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params), KoinComponent {

    override suspend fun doWork(): Result {
        return try {
            val currencyRepository: CurrencyRepository by inject()
            currencyRepository.loadCurrencyList()
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }
}
