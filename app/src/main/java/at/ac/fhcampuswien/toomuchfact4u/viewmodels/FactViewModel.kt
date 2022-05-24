package at.ac.fhcampuswien.toomuchfact4u.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.toomuchfact4u.Fact
import at.ac.fhcampuswien.toomuchfact4u.api.fetchFact
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository
import at.ac.fhcampuswien.toomuchfact4u.widgets.simpleNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FactViewModel( private val repository: FactRepository ) : ViewModel() {

    private var _facts = MutableStateFlow<List<Fact>>(emptyList())
    val facts = _facts.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllFacts().distinctUntilChanged()
                .collect { listOfFacts ->
                    if(listOfFacts.isNullOrEmpty()){
                        Log.d("FactViewModel", "Empty fact list")
                    } else {
                        _facts.value = listOfFacts
                    }
                }
        }
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllFacts().collect { listOfFacts ->
                if(listOfFacts.isNullOrEmpty()){
                    Log.d("FactViewModel", "No facts 4 U")
                } else {
                    _facts.value = listOfFacts
                }
            }
        }
    }

    private var categoryUrl = ""

    private var useCategoryInUrl = false

    private val _categories = listOf("All Categories", "Sports", "History")

    @SuppressLint("StaticFieldLeak")
    private var _context: Context? = null
    private val CHANNEL_ID = "FactNotifications"
    private val notificationId = 0

    private var factFrequency = 5f

    /*fun getNextFactFromQueue() : Fact {
        //TODO null _facts abfangen
        Log.i("Fact Array in get",_facts.toString()) // debugging
        val fact = _facts[0]
        return fact
    }*/
    fun getFactsFromVM(): StateFlow<List<Fact>> {
        return facts
    }

    fun removeFact(fact: Fact) {
        //TODO null _facts abfangen
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFact(fact)
        }
    }

    fun fetchNewFactTest() {
        val fact = fetchFact(use_category = useCategoryInUrl, category = categoryUrl)

        _context?.let { // Notification
            simpleNotification(
                context = it,
                channelId = CHANNEL_ID,
                notificationId = notificationId,
                textContent = "A new Fact has arrived 4 U.",
                priority = NotificationCompat.PRIORITY_HIGH
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.addFact(fact)
        }

        /*val retrofit = Retrofit.Builder()
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

                    if (answers != null) {
                        shuffle(answers)
                    }

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

        }*/
    }

    fun getNumOfFactsInDB() : Int {
        var size = 0
        viewModelScope.launch(Dispatchers.IO) {
            size = repository.getFactCount()
        }
        return size
    }

    fun getCategoryList() : List<String>{
        return _categories
    }
    fun setCategory(index: Int){
        when(index) {
            0 -> {categoryUrl = ""
                    useCategoryInUrl = false} //All
            1 -> {categoryUrl = "21"
                    useCategoryInUrl = true} //Sports
            2 -> {categoryUrl = "23"
                    useCategoryInUrl = true} //History
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
    fun getNotificationContext() : Context? {
        return _context
    }

    fun getCHANNEL_ID() : String{
        return CHANNEL_ID
    }
    fun getNotificationId() : Int{
        return notificationId
    }

    fun setFactFrequency(frequency: Float){
        factFrequency = frequency
    }
    fun getFactFrequency() : Float {
        return factFrequency
    }
}