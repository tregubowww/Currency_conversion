package ru.myuniquenickname.myapplication.data.work_manager

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import java.util.concurrent.TimeUnit

class WorkConstraints {
    private val constraints =
        Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    val constrainedRequest = PeriodicWorkRequest.Builder(
        CurrencyWorker::class.java,
        INTERVAL_REPEAT_LOAD,
        TimeUnit.MINUTES
    )
        .setConstraints(constraints)
        .build()

    companion object {
        const val INTERVAL_REPEAT_LOAD: Long = 15
    }
}
