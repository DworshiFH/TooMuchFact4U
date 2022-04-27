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


@Preview(showBackground = true)
@Composable
fun DetailScreenQuestion() {
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
            AnswerButton()
            AnswerButton()
            AnswerButton()

    }}
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
fun AnswerButton(){
    MaterialTheme {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            onClick = { /* ... */ },
            text = { Text("Answer",
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