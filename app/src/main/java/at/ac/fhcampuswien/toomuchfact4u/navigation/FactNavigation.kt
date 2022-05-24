package at.ac.fhcampuswien.toomuchfact4u.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.toomuchfact4u.db.FactDB
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository
import at.ac.fhcampuswien.toomuchfact4u.screens.home.HomeScreen
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModel
import at.ac.fhcampuswien.toomuchfact4u.screens.detail.DetailScreen
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModelFactory
import at.ac.fhcampuswien.toomuchfact4u.widgets.createNotificationChannel

@Composable
fun FactNavigation(){
    val context = LocalContext.current

    val navController = rememberNavController()
    val db = FactDB.getDatabase(context = context)
    val repository = FactRepository(dao = db.factDao())

    val factVM: FactViewModel = viewModel(factory = FactViewModelFactory(repository = repository))

    createNotificationChannel(channelId = "FactNotifications", context = context)
    factVM.setNotificationContext(context = context)

    NavHost(navController = navController, startDestination = FactScreens.HomeScreen.name){
        composable(
            route = FactScreens.HomeScreen.name
        ) {
            HomeScreen(navController, factVM)
        }

        composable(
            route = FactScreens.DetailScreen.name
        ){
            DetailScreen(factVM = factVM, navController = navController)
        }
    }
}