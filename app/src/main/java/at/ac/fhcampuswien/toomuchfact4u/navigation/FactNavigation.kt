package at.ac.fhcampuswien.toomuchfact4u.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.toomuchfact4u.db.FactDB
import at.ac.fhcampuswien.toomuchfact4u.db.SettingsDB
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository
import at.ac.fhcampuswien.toomuchfact4u.repositories.SettingsRepository
import at.ac.fhcampuswien.toomuchfact4u.screens.settings.SettingsScreen
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModel
import at.ac.fhcampuswien.toomuchfact4u.screens.home.HomeScreen
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModelFactory
import at.ac.fhcampuswien.toomuchfact4u.widgets.createNotificationChannel

@Composable
fun FactNavigation(){
    val context = LocalContext.current

    val navController = rememberNavController()
    val factDb = FactDB.getDatabase(context = context)
    val factRepository = FactRepository(dao = factDb.factDao())

    val settingsDB = SettingsDB.getDatabase(context = context)
    val settingsRepository = SettingsRepository(dao = settingsDB.settingsDao() )

    val factVM: FactViewModel = viewModel(
        factory = FactViewModelFactory(
            factRepository = factRepository,
            settingsRepository = settingsRepository)
    )

    createNotificationChannel(channelId = "FactNotifications", context = context)
    factVM.SetNotificationContext(context = context)

    val startDestination: String = if(factVM.getNumOfFactsInDB() > 0){
        FactScreens.HomeScreen.name
    } else {
        FactScreens.SettingsScreen.name
    }

    NavHost(navController = navController, startDestination = startDestination){
        composable(
            route = FactScreens.HomeScreen.name,
        ){
            HomeScreen(factVM = factVM, navController = navController)
        }

        composable(
            route = FactScreens.SettingsScreen.name
        ) {
            SettingsScreen(factVM = factVM, navController = navController)
        }
    }
}
