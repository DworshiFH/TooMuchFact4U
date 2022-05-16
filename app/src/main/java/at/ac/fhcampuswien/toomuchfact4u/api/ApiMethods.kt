package at.ac.fhcampuswien.toomuchfact4u.api

import android.util.Log
import at.ac.fhcampuswien.toomuchfact4u.Fact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

fun fetchFact() : Fact{

    var fact = Fact("","", listOf(""),listOf(""))

    val retrofit = Retrofit.Builder()
        .baseUrl("https://opentdb.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(FactsAPI::class.java)
    CoroutineScope(Dispatchers.IO).launch {

        val response = service.getRandomFact()

        withContext(Dispatchers.Main){
            if (response.isSuccessful){
                Log.i("", response.toString())

                Log.i("Fact", response.body().toString())

                fact.question = response.body()?.result?.get(0)?.question
                fact.correct_answer = response.body()?.result?.get(0)?.correct_answer
                fact.incorrect_answers = response.body()?.result?.get(0)?.incorrect_answers as List<String>?

                var answers = fact.incorrect_answers?.plus(fact.correct_answer)

                if (answers != null) {
                    Collections.shuffle(answers)
                }

                fact.all_answers = answers as List<String>?

            } else {
                Log.e("RETROFIT_ERROR", response.code().toString())
            }
        }
    }
    return fact
}