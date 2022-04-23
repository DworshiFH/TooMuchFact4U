package at.ac.fhcampuswien.toomuchfact4u.screens.home

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.toomuchfact4u.Fact

@Preview(showBackground = true)
@Composable
fun HomeScreen(navController: NavController = rememberNavController(), viewModel: FactViewModel = viewModel()){
    Scaffold(
        topBar = {
            TopAppBar( title = {
                Text(text = "2 Much Fact 4 U")
            } )
        }
    ) {
        MainContent(viewModel)
    }
}

@Composable
fun MainContent(/*TODO Navigation Component*/ viewModel: FactViewModel){
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Fact Frequency")
        var sliderPosition by remember { mutableStateOf(0f) }
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                //TODO: Notification
                            },
            valueRange = 1f..5f,
            onValueChangeFinished = {
                Log.i("HomeScreen","Fact Frequency Value Changed")
                //TODO
                //TODO: Notification
            },
            steps = 3
        )

        DropdownCategorySelector(viewModel)

        val useSoundState = remember { mutableStateOf(true)}
        Row(){
            Text(text = "Sound")
            Switch(
                checked = useSoundState.value,
                onCheckedChange = {
                    useSoundState.value = it
                    viewModel.setUseSound(it)
                    //TODO: Notification
                }
            )
        }

        val factAsQuestionCheckedState = remember { mutableStateOf(false)}
        Row(){
            Text(text = "Display Fact as Question")
            Switch(
                checked = factAsQuestionCheckedState.value,
                onCheckedChange = {
                    factAsQuestionCheckedState.value = it
                    viewModel.setDisplayFactAsQuestion(it)
                    //TODO: Notification
                }
            )
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
        .fillMaxWidth()) {
        Text(text = items[selectedIndex],
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = true }),
        style = MaterialTheme.typography.h5)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    viewModel.setCategory(index)
                    Log.i("HomeScreen", "index = $index")
                    Log.i("HomeScreen", "Category = " + viewModel.getURL())
                }) {
                    Text(text = items[index])
                }
            }
        }
    }
}

