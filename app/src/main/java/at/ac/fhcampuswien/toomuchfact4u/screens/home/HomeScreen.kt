package at.ac.fhcampuswien.toomuchfact4u.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
fun HomeScreen(navController: NavController, factVM: FactViewModel = viewModel()){
    Scaffold(
        topBar = {
            TopAppBar( title = {
                Text(text = "2 Much Fact 4 U")
            } )
        }
    ) {
        MainContent(navController, factVM)
    }
}

@Composable
fun MainContent(navController: NavController, factVM: FactViewModel){

    val context = factVM.getNotificationContext()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "\"2 Much Fact 4 U\" will now automatically fetch the newest and hottest Facts 4 U. Sit back, wait and enjoy your random dose of trivia.")

        Divider()

        Surface(modifier = Modifier.height(10.dp)) {
            
        } //height separation

        Text(text = "Settings", style = MaterialTheme.typography.h5)
        
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),) {
            Column(){
                Text(text = "Fact Frequency")
                var sliderPosition by remember { mutableStateOf(0f) }
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        context?.let {
                            simpleNotification(
                                context = it,
                                channelId = factVM.getCHANNEL_ID(),
                                notificationId = 3,
                                textContent = "Fact Frequency Changed!",
                                priority = NotificationCompat.PRIORITY_HIGH
                            )
                        }
                    },
                    valueRange = 1f..5f,
                    onValueChangeFinished = {
                        Log.i("HomeScreen","Fact Frequency Value Changed")
                        //TODO
                        //TODO: Notification
                        factVM.setFactFrequency(sliderPosition)
                        context?.let {
                            simpleNotification(
                                context = it,
                                channelId = factVM.getCHANNEL_ID(),
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

        DropdownCategorySelector(factVM)

        Divider()

        val useSoundState = remember { mutableStateOf(true)}
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()){
            Column(modifier = Modifier.width(300.dp)) {
                Text(text = "Sound", style = MaterialTheme.typography.body1)
            }
            Column(modifier = Modifier.width(100.dp)) {
                Switch( // sound Switch
                    checked = useSoundState.value,
                    onCheckedChange = {
                        useSoundState.value = it
                        factVM.setUseSound(it)
                        var text = ""
                        context?.let {
                            if(useSoundState.value){
                                text = "Now using pleasant sounds."
                            } else {
                                text = "No longer using pleasant sounds."
                            }
                            simpleNotification(
                                context = it,
                                channelId = factVM.getCHANNEL_ID(),
                                notificationId = 1,
                                textContent = text,
                                priority = NotificationCompat.PRIORITY_HIGH
                            )

                        }
                    }
                )
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
                Switch( // Display Fact as Question Switch
                    checked = factAsQuestionCheckedState.value,
                    onCheckedChange = {
                        factAsQuestionCheckedState.value = it
                        factVM.setDisplayFactAsQuestion(it)
                        context?.let {

                            var text = ""
                            if(factAsQuestionCheckedState.value){
                                text = "Now displaying Facts as Questions, exciting."
                            } else {
                                text = "Now simply displaying Facts, boring."
                            }

                            simpleNotification(
                                context = it,
                                channelId = factVM.getCHANNEL_ID(),
                                notificationId = 1,
                                textContent = text,
                                priority = NotificationCompat.PRIORITY_HIGH
                            )
                        }
                    }
                )
            }
        }
        
        Divider()
        Button(onClick = {
            factVM.fetchNewFactTest()
        }) {
            Text(text = "Fetch Fact")
        }
        
        Divider()
        Button(onClick = {
            navController.navigate(route = FactScreens.DetailScreen.name)
        }) {
            Text(text = "Go To DetailScreen")
        }
    }
}

@Composable
fun DropdownCategorySelector(viewModel: FactViewModel) {
    var expanded by remember { mutableStateOf(false) }

    //TODO put these Items into the Viewmodel
    //val items = listOf("Category A", "Category B") //DEBUG without ViewModel
    val items = viewModel.getCategoryList()

    var selectedIndex by remember { mutableStateOf(0) }
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
            items.forEachIndexed { index, s ->
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

