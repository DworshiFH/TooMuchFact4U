package at.ac.fhcampuswien.toomuchfact4u.api

import android.util.Log
import at.ac.fhcampuswien.toomuchfact4u.Fact
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

fun fetchFact(use_category: Boolean = false, category: String = "23", repository: FactRepository){

    var fact = Fact(question = "",
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

        val response = if(use_category){
            service.getFactFromCategory(category)
        } else {
            service.getRandomFact()
        }

        withContext(Dispatchers.Main){
            if (response.isSuccessful){
                Log.i("", response.toString())

                Log.i("Fact", response.body().toString())

                //parsing FactJSONModel object to Fact object
                fact.question = response.body()?.result?.get(0)?.question
                fact.correct_answer = response.body()?.result?.get(0)?.correct_answer
                fact.incorrect_answer_1 = response.body()?.result?.get(0)?.incorrect_answers?.get(0)
                if(response.body()?.result?.get(0)?.incorrect_answers?.get(1) != null){
                    fact.incorrect_answer_2 = response.body()?.result?.get(0)?.incorrect_answers?.get(1)
                }
                if(response.body()?.result?.get(0)?.incorrect_answers?.get(2) != null){
                    fact.incorrect_answer_3 = response.body()?.result?.get(0)?.incorrect_answers?.get(2)
                }

                repository.addFact(fact)

            } else {
                Log.e("RETROFIT_ERROR", response.code().toString())
            }
        }
    }
}