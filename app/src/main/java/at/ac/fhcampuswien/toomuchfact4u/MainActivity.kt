package at.ac.fhcampuswien.toomuchfact4u

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import at.ac.fhcampuswien.toomuchfact4u.db.FactDB
import at.ac.fhcampuswien.toomuchfact4u.navigation.FactNavigation
import at.ac.fhcampuswien.toomuchfact4u.ui.theme.TooMuchFact4UTheme
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModel
import at.ac.fhcampuswien.toomuchfact4u.widgets.*

class MainActivity : ComponentActivity() {

    override fun onStart(){
        super.onStart()
        Log.i("MainActivity", "onStart called")
        //TODO start Fact Thread
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
        setContent {
            TooMuchFact4UTheme {
                // A surface container using the 'background' color from the theme
                MyApp{
                    FactNavigation()
                }
            }
        }
        //TODO start worker
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit){

    TooMuchFact4UTheme() {
        content()
    }
}

