package at.ac.fhcampuswien.toomuchfact4u.api

import android.content.Context
import android.os.Build
import android.text.Html
import android.util.Log
import androidx.core.app.NotificationCompat
import at.ac.fhcampuswien.toomuchfact4u.dataModels.Fact
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository
import at.ac.fhcampuswien.toomuchfact4u.widgets.notificationWithLaunchApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun fetchFact(category: String = "", repository: FactRepository, context: Context){

    val fact = Fact(question = "",
        correct_answer = "",
        incorrect_answer_1 = "",
        incorrect_answer_2 = "",
        incorrect_answer_3 = "",
    )

    val retrofit = Retrofit.Builder()
        .baseUrl("https://opentdb.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(FactsAPI::class.java)
    CoroutineScope(Dispatchers.IO).launch {

        val response = if(category == ""){
            service.getRandomFact()
        } else {
            service.getFactFromCategory(category)
        }

        withContext(Dispatchers.Main){
            if (response.isSuccessful){
                Log.i("", response.toString())

                Log.i("Fact", response.body().toString())

                //parsing FactJSONModel object to Fact object
                fact.question = response.body()?.result?.get(0)?.question?.let {
                    convertHtmlToString(it)
                }
                fact.correct_answer = response.body()?.result?.get(0)?.correct_answer?.let {
                    convertHtmlToString(it)
                }
                fact.incorrect_answer_1 = convertHtmlToString(response.body()?.result?.get(0)?.incorrect_answers?.get(0)!!)
                fact.incorrect_answer_2 = convertHtmlToString(response.body()?.result?.get(0)?.incorrect_answers?.get(1)!!)
                fact.incorrect_answer_3 = convertHtmlToString(response.body()?.result?.get(0)?.incorrect_answers?.get(2)!!)

                repository.addFact(fact)

                notificationWithLaunchApp(
                    context = context,
                    notificationId = 0,
                    textContent = "A new Fact has arrived 4 U, tap here to view it.",
                    priority = NotificationCompat.PRIORITY_HIGH
                )

            } else {
                Log.e("RETROFIT_ERROR", response.code().toString())
            }
        }
    }
}

fun convertHtmlToString(str: String): String {
    val retStr = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(str).toString()
    }
    return retStr
}