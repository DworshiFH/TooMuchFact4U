package at.ac.fhcampuswien.toomuchfact4u.screens.detail

import android.R
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.toomuchfact4u.Fact
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModel

@Composable
fun DetailScreen(factVM : FactViewModel) {

    val fact = factVM.getNextFactFromQueue()

    Scaffold(
        topBar = {
            TopAppBar{
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back" )
                    Text(text = "Detail Screen", style = MaterialTheme.typography.h6)
                }
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            fact.question?.let { it1 ->
                Text(text = it1,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(30.dp))
            }
            if(factVM.getDisplayFactAsQuestion()){
                for(f in fact.all_answers!!){
                    fact.correct_answer?.let { it1 -> AnswerButton(f, it1, true){
                        factVM.removeTopFactFromQueue()
                    } }
                }
            } else {
                fact.correct_answer?.let { it1 -> AnswerButton(it1, fact.correct_answer!!, false) }
            }
        }
    }
}

@Composable
fun AnswerButton(text: String, correctAnswer: String, displayFactAsQuestion: Boolean, code: () -> Unit = {}){
    MaterialTheme {
        var color = remember{ mutableStateOf(Color.White)}
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            backgroundColor = color.value,
            onClick = {
                if(displayFactAsQuestion){
                    if(text == correctAnswer){
                        // richtig
                        color.value = Color.Green
                        code()

                    } else {
                        // falsch
                        color.value = Color.Red
                    }
                    //TODO notification
                }
            },
            text = { Text(text = text, color = Color.Black) }
        )
    }
}

/*@Composable
fun DetailScreenFact() {
    Scaffold(
        topBar = {
            TopAppBar{
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back" )
                    Text(text = "Detail Screen", style = MaterialTheme.typography.h6)
                }

            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Question()
            AnswerButton()

        }}
}*/

@Composable
fun Question(){
    Text("Question?",
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        modifier = Modifier
            .padding(30.dp))
}