package at.ac.fhcampuswien.toomuchfact4u.screens.detail

import android.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.ac.fhcampuswien.toomuchfact4u.Fact
import at.ac.fhcampuswien.toomuchfact4u.navigation.FactNavigation
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModel


@Composable
fun DetailScreenQuestion(fact : Fact, displayFactAsQuestion: Boolean) {
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
                for(f in fact.all_answers){
                    AnswerButton(f)
                }
            } else {
                fact.correct_answer?.let { it1 -> AnswerButton(it1) }
            }
        }
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
fun AnswerButton(text: String = "answer"){
    MaterialTheme {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            onClick = { /* ... */ },
            text = { Text(text,
            fontSize = 20.sp)
            }
        )

}}

@Composable
fun Question(){
    Text("Question?",
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        modifier = Modifier
            .padding(30.dp),)
}