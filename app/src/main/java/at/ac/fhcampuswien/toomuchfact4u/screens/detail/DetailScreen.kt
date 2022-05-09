package at.ac.fhcampuswien.toomuchfact4u.screens.detail

import android.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.ac.fhcampuswien.toomuchfact4u.Fact
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModel

@Composable
fun DetailScreenQuestion(factVM : FactViewModel, displayFactAsQuestion: Boolean) {

    val fact: Fact = factVM.getNextFactFromQueue()

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
            if(displayFactAsQuestion){
                for(f in fact.all_answers!!){
                    fact.correct_answer?.let { it1 -> AnswerButton(f, it1, true) }
                }
            } else {
                fact.correct_answer?.let { it1 -> AnswerButton(it1, fact.correct_answer!!, false) }
            }
        }
    }
}

@Composable
fun AnswerButton(text: String = "answer", correctAnswer: String, displayFactAsQuestion: Boolean){
    MaterialTheme {
        var color by mutableStateOf(Color.White)
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            backgroundColor = color,
            onClick = {
                if(displayFactAsQuestion){
                    if(text == correctAnswer){
                        // richtig
                        color = Color.Green
                        //TODO delete Fact when correct answer has been clicked
                    } else {
                        // falsch
                        color = Color.Red
                    }
                    //TODO notification
                }
            },
            text = { Text(text,
            fontSize = 20.sp)
            }
        )
}}

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
            .padding(30.dp),)
}