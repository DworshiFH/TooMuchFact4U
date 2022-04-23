package at.ac.fhcampuswien.toomuchfact4u.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.toomuchfact4u.screens.home.HomeScreen
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModel

@Composable
fun FactNavigation(){
    val navController = rememberNavController()

    val factVM: FactViewModel = viewModel()

    NavHost(navController = navController, startDestination = FactScreens.HomeScreen.name){
        composable(
            route = FactScreens.HomeScreen.name
        ) {
            HomeScreen(navController, factVM)
        }
    }

}