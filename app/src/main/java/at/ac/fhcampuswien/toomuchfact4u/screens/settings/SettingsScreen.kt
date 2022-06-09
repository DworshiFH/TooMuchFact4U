package at.ac.fhcampuswien.toomuchfact4u.screens.settings

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.toomuchfact4u.navigation.FactScreens
import at.ac.fhcampuswien.toomuchfact4u.widgets.simpleNotification

@Composable
fun SettingsScreen(factVM: FactViewModel = viewModel(), navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar( title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable{
                            navController.navigate(route = FactScreens.HomeScreen.name)
                        })
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "2 Much Fact 4 U Settings")
                }
            } )
        }
    ) {
        MainContent(factVM)
    }
}

@Composable
fun MainContent(factVM: FactViewModel){

    val context = factVM.getNotificationContext()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "\"2 Much Fact 4 U\" will automatically fetch the newest and hottest Facts 4 U. Sit back, wait and enjoy your random dose of trivia.")

        Divider()

        Surface(modifier = Modifier.height(10.dp)) {
            //height separation
        }

        Text(text = "Settings", style = MaterialTheme.typography.h5)
        
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),) {
            Column{
                Text(text = "Fact Frequency")
                var sliderPosition by remember { mutableStateOf(factVM.getFactFrequency()) }
                sliderPosition?.let {
                    Slider(
                        value = it,
                        onValueChange = {
                            sliderPosition = it
                            context?.let {
                                simpleNotification(
                                    context = it,
                                    notificationId = 3,
                                    textContent = "Fact Frequency Changed!",
                                    priority = NotificationCompat.PRIORITY_HIGH
                                )
                            }
                        },
                        valueRange = 1f..5f,
                        onValueChangeFinished = {
                            Log.i("HomeScreen","Fact Frequency Value Changed")
                            factVM.setFactFrequency(sliderPosition!!)
                            context?.let {
                                simpleNotification(
                                    context = it,
                                    notificationId = 4,
                                    textContent = "Fact Frequency Set to " + factVM.getFactFrequency().toString(),
                                    priority = NotificationCompat.PRIORITY_HIGH
                                )
                            }
                        },
                        steps = 3
                    )
                }
            }
        }

        DropdownCategorySelector(factVM)

        Divider()

        val useSoundState = remember { mutableStateOf(factVM.getUseSound())}
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()){
            Column(modifier = Modifier.width(300.dp)) {
                Text(text = "Sound", style = MaterialTheme.typography.body1)
            }
            Column(modifier = Modifier.width(100.dp)) {
                useSoundState.value?.let {
                    Switch( // sound Switch
                        checked = it,
                        onCheckedChange = {
                            useSoundState.value = it
                            factVM.setUseSound(it)
                            context?.let {
                                val text = if(useSoundState.value!!){
                                    "Now using pleasant sounds."
                                } else {
                                    "No longer using pleasant sounds."
                                }
                                simpleNotification(
                                    context = it,
                                    notificationId = 1,
                                    textContent = text,
                                    priority = NotificationCompat.PRIORITY_HIGH
                                )

                            }
                        }
                    )
                }
            }
        }

        Divider()

        val factAsQuestionCheckedState = remember { mutableStateOf(factVM.getDisplayFactAsQuestion())}
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()){
            Column(modifier = Modifier.width(300.dp)){
                Text(text = "Display Fact as Question", style = MaterialTheme.typography.body1)
            }
            Column(modifier = Modifier.width(100.dp)) {
                factAsQuestionCheckedState.value?.let {
                    Switch( // Display Fact as Question Switch
                        checked = it,
                        onCheckedChange = {
                            factAsQuestionCheckedState.value = it
                            factVM.setDisplayFactAsQuestion(it)
                            context?.let {

                                val text = if(factAsQuestionCheckedState.value!!){
                                    "Now displaying Facts as questions, exciting."
                                } else {
                                    "Now simply displaying Facts, boring."
                                }

                                simpleNotification(
                                    context = it,
                                    notificationId = 1,
                                    textContent = text,
                                    priority = NotificationCompat.PRIORITY_HIGH
                                )
                            }
                        }
                    )
                }
            }
        }
        
        Divider()

        val numOfFactsInDb = remember{ mutableStateOf(1) }
        numOfFactsInDb.value = factVM.getNumOfFactsInDB()

        if(numOfFactsInDb.value == 0){
            Text(text = "We have no Facts stored 4 U. Come back later or press the back arrow to instantly crash the app.")
        }

        //Testing Buttons
        /*val scope = rememberCoroutineScope()
        Button(onClick = {
            scope.launch {
                factVM.fetchNewFactTest()
            }
        }) {
            Text(text = "Fetch Fact")
        }

        Divider()
        Button(onClick = {
            navController.navigate(route = FactScreens.DetailScreen.name)
        }) {
            Text(text = "Go To DetailScreen")
        }*/
    }
}

@Composable
fun DropdownCategorySelector(viewModel: FactViewModel) {
    var expanded by remember { mutableStateOf(false) }

    val items = viewModel.getCategoryList()

    var selectedIndex by remember { mutableStateOf(viewModel.getSelectedCategory()) }
    Box(modifier = Modifier
        .padding(5.dp)
        .clickable(onClick = { expanded = true })
        .fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.Start){
            Text(text = items[selectedIndex],
                style = MaterialTheme.typography.body1)
            if(expanded){
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Arrow Up")
            } else {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Arrow Down")
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items.forEachIndexed { index, _ ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    viewModel.setCategory(index)
                    Log.i("HomeScreen", "index = $index")
                }) {
                    Text(text = items[index], style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}

