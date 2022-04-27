package at.ac.fhcampuswien.toomuchfact4u.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.toomuchfact4u.Fact
import at.ac.fhcampuswien.toomuchfact4u.api.FactsAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class FactViewModel : ViewModel() {
    private val _facts = mutableListOf<Fact>()

    private val factList: List<Fact> by mutableStateOf(listOf())

    var errorMessage: String by mutableStateOf("")

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

            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val items = response.body()
                    Log.i("", response.toString())
                    if(items != null) {
                        for (i in 0 until items.count()){
                            val question = items[i].question ?: "N/A"
                            Log.i("question", question)

                            val correct_answer = items[i].correct_answer ?: "N/A"
                            Log.i("correct_answer", correct_answer)
                        }
                    }
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
            0 -> setURL("https://opentdb.com/api.php?amount=1") //All
            1 -> setURL("https://opentdb.com/api.php?amount=1&category=21") //Sports
            2 -> setURL("https://opentdb.com/api.php?amount=1&category=23") //History
        }
    }

    private var URL = "https://opentdb.com/api.php?amount=1&type=multiple"
    fun setURL(URL: String){
        this.URL = URL
    }
    fun getURL(): String{
        return URL
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