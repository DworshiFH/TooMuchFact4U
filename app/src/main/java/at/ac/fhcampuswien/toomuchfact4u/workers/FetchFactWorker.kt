package at.ac.fhcampuswien.toomuchfact4u.workers

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.*
import at.ac.fhcampuswien.toomuchfact4u.api.fetchFact
import at.ac.fhcampuswien.toomuchfact4u.db.FactDB
import at.ac.fhcampuswien.toomuchfact4u.db.SettingsDB
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository
import at.ac.fhcampuswien.toomuchfact4u.repositories.SettingsRepository
import at.ac.fhcampuswien.toomuchfact4u.widgets.notificationWithLaunchApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class FetchFactWorker(context: Context, workerParams: WorkerParameters):
    Worker(context, workerParams){

    val context = context

    override fun doWork(): Result{

        val factDb = FactDB.getDatabase(context = context)
        val factRepository = FactRepository(dao = factDb.factDao())

        val settingsDB = SettingsDB.getDatabase(context = context)
        val settingsRepository = SettingsRepository(dao = settingsDB.settingsDao())

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            //fetch a new fact
            val settings = settingsRepository.getSettings()

            settings.category?.let { fetchFact(category = it, repository = factRepository, context = context) }

            Log.i("FetchFactWorker", "success == true")
        }

        scope.launch {
            //dispatch a new FetchFactWorker

            val frequencyPerHour = settingsRepository.getSettings().fact_frequency //returns a num between 1 and 5,

            // this num represents the number of times per hour a fact shall be fetched
            val frequencyPerHourInt: Int = frequencyPerHour?.roundToInt() ?: Int.MIN_VALUE

            val frequencyInMinutes: Int = 60/frequencyPerHourInt

            val randomVal = (-5..5).random()

            val currentTime = Calendar.getInstance()
            val dueTime = Calendar.getInstance()

            dueTime.add(Calendar.MINUTE, frequencyInMinutes)

            if (dueTime.before(currentTime)){
                dueTime.add(Calendar.MINUTE, frequencyInMinutes)
            }

            val timeDiff = dueTime.timeInMillis - currentTime.timeInMillis + randomVal

            val fetchFactRequest = OneTimeWorkRequestBuilder<FetchFactWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(applicationContext).enqueueUniqueWork(
                "fetchFactRequest",
                ExistingWorkPolicy.KEEP,
                fetchFactRequest)
        }

        return Result.success()
    }
}