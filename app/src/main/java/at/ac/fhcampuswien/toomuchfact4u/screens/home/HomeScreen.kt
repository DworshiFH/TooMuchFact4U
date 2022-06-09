package at.ac.fhcampuswien.toomuchfact4u.screens.home

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import at.ac.fhcampuswien.toomuchfact4u.navigation.FactScreens
import at.ac.fhcampuswien.toomuchfact4u.viewmodels.FactViewModel
import at.ac.fhcampuswien.toomuchfact4u.widgets.simpleNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(factVM : FactViewModel, navController : NavController) {

    val fact = factVM.getFactsFromVM()[0]

    Scaffold(
        topBar = {
            TopAppBar{
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = " 2 Much Fact 4 U", style = MaterialTheme.typography.h6)
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
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(30.dp))
            }
            if(factVM.getDisplayFactAsQuestion() == true){
                val allAnswers = mutableListOf<String>()

                val activity = LocalContext.current as? Activity

                fact.incorrect_answer_1?.let { it1 -> allAnswers.add(it1) }
                fact.incorrect_answer_2?.let { it1 -> allAnswers.add(it1) }
                fact.incorrect_answer_3?.let { it1 -> allAnswers.add(it1) }
                fact.correct_answer?.let { it1 -> allAnswers.add(it1) }

                Log.i("DetailScreenAllAnswers", allAnswers.toString())

                allAnswers.shuffle()

                for(f in allAnswers){
                    fact.correct_answer?.let { it1 -> AnswerButton(f, it1, true,
                        codeRightAnswer = {
                            factVM.deleteFact(fact)
                            val context = factVM.getNotificationContext()
                            context?.let {
                                simpleNotification(
                                    context = it,
                                    notificationId = 1,
                                    textContent = "Correct Answer, you are very smart.",
                                    priority = NotificationCompat.PRIORITY_HIGH
                                )
                                val scope = CoroutineScope(Dispatchers.Main)
                                scope.launch {
                                    delay(2000)
                                    activity?.finish()
                                }
                            }
                        }, codeWrongAnswer = {
                            val context = factVM.getNotificationContext()
                            context?.let {
                                simpleNotification(
                                    context = it,
                                    notificationId = 1,
                                    textContent = "Wrong Answer, that was pretty stupid!",
                                    priority = NotificationCompat.PRIORITY_HIGH
                                )
                            }
                        })
                    }
                }
            } else {
                fact.correct_answer?.let { it1 -> AnswerButton(it1, fact.correct_answer!!, false) }
                factVM.deleteFact(fact)
            }

            Divider()

            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                navController.navigate(route = FactScreens.SettingsScreen.name)
            }) {
                Text(text = "Settings")
            }
        }
    }
}

@Composable
fun AnswerButton(text: String,
                 correctAnswer: String,
                 displayFactAsQuestion: Boolean,
                 codeRightAnswer: () -> Unit = {},
                 codeWrongAnswer: () -> Unit = {}){
    MaterialTheme {
        val color = remember{ mutableStateOf(Color(61, 156, 220))}
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            backgroundColor = color.value,
            onClick = {
                if(displayFactAsQuestion){
                    if(text == correctAnswer){
                        // right answer
                        color.value = Color.Green
                        codeRightAnswer()
                    } else {
                        // wrong answer
                        color.value = Color.Red
                        codeWrongAnswer()
                    }
                }
            },
            text = { Text(text = text, color = Color.Black, textAlign = TextAlign.Center) }
        )
    }
}