package at.ac.fhcampuswien.toomuchfact4u.services

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import at.ac.fhcampuswien.toomuchfact4u.api.Fact
import at.ac.fhcampuswien.toomuchfact4u.api.fetchFact
import at.ac.fhcampuswien.toomuchfact4u.db.FactDB
import at.ac.fhcampuswien.toomuchfact4u.navigation.FactNavigation
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository

class DownloadWorker(context: Context, workerParams: WorkerParameters):
    Worker(context, workerParams){

    val context = context

    override fun doWork(): Result{

        val db = FactDB.getDatabase(context = context)
        val repository = FactRepository(dao = db.factDao())

        fetchFact(use_category = false, repository = repository)

        return Result.success( workDataOf("fact" to "") )
    }
}