package at.ac.fhcampuswien.toomuchfact4u.services

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import at.ac.fhcampuswien.toomuchfact4u.api.fetchFact

class DownloadWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams){
    override fun doWork(): Result{
        //val fact = fetchFact()
        return Result.success( workDataOf("fact" to ""/*fact.toString()*/) )
    }
}