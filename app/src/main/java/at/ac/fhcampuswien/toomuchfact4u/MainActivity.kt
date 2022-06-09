package at.ac.fhcampuswien.toomuchfact4u

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import at.ac.fhcampuswien.toomuchfact4u.navigation.FactNavigation
import at.ac.fhcampuswien.toomuchfact4u.workers.FetchFactWorker
import at.ac.fhcampuswien.toomuchfact4u.workers.NotifyNumOfFactsInDbWorker
import at.ac.fhcampuswien.toomuchfact4u.ui.theme.TooMuchFact4UTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    override fun onStart(){
        super.onStart()
        Log.i("MainActivity", "onStart called")
    }

    override fun onResume(){
        super.onResume()
        Log.i("MainActiviy", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActiviy", "onPause called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActiviy", "onRestart called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActiviy", "onStop called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("MainActiviy", "onDestroy called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fetchFactRequest = OneTimeWorkRequestBuilder<FetchFactWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        val workManager = WorkManager.getInstance(applicationContext)

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            "fetchFactRequest",
            ExistingWorkPolicy.KEEP,
            fetchFactRequest)

        val notifyNumOfFactsInDbRequest = OneTimeWorkRequestBuilder<NotifyNumOfFactsInDbWorker>()
            .setInitialDelay(60, TimeUnit.MINUTES)
            .build()

        workManager.beginUniqueWork(
            "notifyNumOfFactsInDb",
            ExistingWorkPolicy.KEEP,
            notifyNumOfFactsInDbRequest
        ).enqueue()

        setContent {
            TooMuchFact4UTheme {
                // A surface container using the 'background' color from the theme
                MyApp{
                    FactNavigation()
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit){
    TooMuchFact4UTheme() {
        content()
    }
}

