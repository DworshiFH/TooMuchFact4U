package at.ac.fhcampuswien.toomuchfact4u.workers

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.*
import at.ac.fhcampuswien.toomuchfact4u.db.FactDB
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository
import at.ac.fhcampuswien.toomuchfact4u.widgets.notificationWithLaunchApp
import java.util.*
import java.util.concurrent.TimeUnit

class NotifyNumOfFactsInDbWorker(context: Context, workerParams: WorkerParameters):
    Worker(context, workerParams){

    val context = context

    override fun doWork(): Result{
        val db = FactDB.getDatabase(context = context)
        val repository = FactRepository(dao = db.factDao())

        val size = repository.getFactCount()

        notification(size)

        val currentTime = Calendar.getInstance()
        val dueTime = Calendar.getInstance()

        dueTime.add(Calendar.HOUR, 1)

        if (dueTime.before(currentTime)){
            dueTime.add(Calendar.HOUR, 1)
        }

        val timeDiff = dueTime.timeInMillis - currentTime.timeInMillis

        val notifyNumOfFactsInDbRequest = OneTimeWorkRequestBuilder<NotifyNumOfFactsInDbWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(applicationContext).beginUniqueWork(
            "notifyNumOfFactsInDb",
            ExistingWorkPolicy.KEEP,
            notifyNumOfFactsInDbRequest
        ).enqueue()

        return Result.success()
    }

    private fun notification(size: Int){
        notificationWithLaunchApp(
            context = context,
            notificationId = 5,
            textContent = "We have $size facts 4 U stored, tap here to view them",
            priority = NotificationCompat.PRIORITY_HIGH
        )
    }
}