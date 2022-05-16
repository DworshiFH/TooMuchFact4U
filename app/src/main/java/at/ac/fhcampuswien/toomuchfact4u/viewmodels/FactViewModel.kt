package at.ac.fhcampuswien.toomuchfact4u.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.toomuchfact4u.Fact
import at.ac.fhcampuswien.toomuchfact4u.api.FactsAPI
import at.ac.fhcampuswien.toomuchfact4u.widgets.simpleNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FactViewModel : ViewModel() {
    private var _facts = mutableListOf<Fact>()

    private var category_url = ""

    private val _categories = listOf("All Categories", "Sports", "History")

    @SuppressLint("StaticFieldLeak")
    private var _context: Context? = null
    private val CHANNEL_ID = "FactNotifications"
    private val notificationId = 0


    fun getNextFactFromQueue() : Fact {
        //TODO null _facts abfangen
        Log.i("Fact Array in get",_facts.toString()) // debugging
        val fact = _facts[0]
        return fact
    }

    fun removeTopFactFromQueue() {
        //TODO null _facts abfangen
        _facts.removeAt(0)
        Log.i("Fact Array in remove",_facts.toString()) // debugging
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
                    Log.i("", response.toString())

                    Log.i("Fact", response.body().toString())

                    var fact = Fact("","", listOf(""),listOf(""))

                    fact.question = response.body()?.result?.get(0)?.question
                    fact.correct_answer = response.body()?.result?.get(0)?.correct_answer
                    fact.incorrect_answers = response.body()?.result?.get(0)?.incorrect_answers as List<String>?

                    var answers = fact.incorrect_answers?.plus(fact.correct_answer)

                    fact.all_answers = answers as List<String>?

                    Log.i("Fact Object", fact.toString())

                    _facts.add(fact)

                    Log.i("Fact Array",_facts.toString()) // debugging

                    _context?.let {
                        simpleNotification(
                            context = it,
                            channelId = CHANNEL_ID,
                            notificationId = notificationId,
                            textContent = "A new Fact has arrived 4 U.",
                            priority = NotificationCompat.PRIORITY_HIGH
                        )
                    }

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }

        }
    }

    fun getNumOfFactsInQueue() : Int {
        return _facts.size
    }

    fun getCategoryList() : List<String>{
        return _categories
    }
    fun setCategory(index: Int){
        when(index) {
            0 -> category_url = "" //All
            1 -> category_url = "category=21" //Sports
            2 -> category_url = "category=23" //History
        }
    }

    private var displayFactAsQuestion = true
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

    @Composable
    fun setNotificationContext(context: Context){
        _context = context
    }


}