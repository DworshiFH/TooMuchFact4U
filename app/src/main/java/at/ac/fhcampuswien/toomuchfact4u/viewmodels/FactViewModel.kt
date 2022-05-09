package at.ac.fhcampuswien.toomuchfact4u.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.toomuchfact4u.Fact
import at.ac.fhcampuswien.toomuchfact4u.api.FactsAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FactViewModel : ViewModel() {
    private val _facts = mutableListOf<Fact>()

    fun getNextFactFromQueue() : Fact {
        val fact = _facts[0]
        _facts.removeAt(0)
        return fact
    }

    fun fetchNewFact() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FactsAPI::class.java)
        CoroutineScope(Dispatchers.IO).launch {

            val response = service.getRandomFact()

            //val resCategory = service.getFactFromCategory()

            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    Log.i("", response.toString())

                    Log.i("Fact", response.body().toString())

                    //TODO Nullable abfangen

                    var fact = Fact("","", listOf(""),listOf(""))

                    fact.question = response.body()?.result?.get(0)?.question
                    fact.correct_answer = response.body()?.result?.get(0)?.correct_answer
                    fact.incorrect_answers = response.body()?.result?.get(0)?.incorrect_answers as List<String>?

                    var answers = fact.incorrect_answers?.plus(fact.correct_answer)

                    fact.all_answers = answers as List<String>?

                    Log.i("Fact Object", fact.toString())

                    _facts.plus(fact)

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }

        }
    }

    fun addFactToQueue(fact: Fact){
        _facts.add(fact)
    }

    fun getNumOfFactsInQueue() : Int {
        return _facts.size
    }

    private val _categories = listOf("All Categories", "Sports", "History")
    fun getCategoryList() : List<String>{
        return _categories
    }
    fun setCategory(index: Int){
        when(index) {
            0 -> setURL("") //All
            1 -> setURL("category=21") //Sports
            2 -> setURL("category=23") //History
        }
    }

    private var category_url = ""
    fun setURL(url: String){
        this.category_url = url
    }
    fun getURL(): String{
        return category_url
    }

    private var displayFactAsQuestion = false
    fun getDisplayFactAsQuestion(): Boolean{
        return displayFactAsQuestion
    }
    fun setDisplayFactAsQuestion(displayFactAsQuestion: Boolean){
        this.displayFactAsQuestion = displayFactAsQuestion
    }

    private var useSound = true
    fun getUseSound(): Boolean{
        return useSound
    }
    fun setUseSound(useSound: Boolean){
        this.useSound = useSound
    }
}