package at.ac.fhcampuswien.toomuchfact4u.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.toomuchfact4u.api.fetchFact
import at.ac.fhcampuswien.toomuchfact4u.dataModels.Fact
import at.ac.fhcampuswien.toomuchfact4u.dataModels.Settings
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository
import at.ac.fhcampuswien.toomuchfact4u.repositories.SettingsRepository
import at.ac.fhcampuswien.toomuchfact4u.widgets.simpleNotification
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FactViewModel(
    private val factRepository: FactRepository,
    private val settingsRepository: SettingsRepository
    ) : ViewModel() {

    private var _facts = MutableStateFlow<List<Fact>>(emptyList())
    private lateinit var _settings: Settings

    @SuppressLint("StaticFieldLeak")
    private var _context: Context? = null

    init {
        getAllFactsFromDB()
        getSettingsFromDB()
    }

    private fun getAllFactsFromDB(){
        _facts = MutableStateFlow<List<Fact>>(emptyList())
        viewModelScope.launch(Dispatchers.IO){
            factRepository.getAllFacts().distinctUntilChanged()
                .collect { listOfFacts ->
                    if(listOfFacts.isNullOrEmpty()){
                        Log.d("FactViewModel", "Empty fact list")
                    } else {
                        _facts.value = listOfFacts
                    }
                }
        }
        viewModelScope.launch(Dispatchers.IO){
            factRepository.getAllFacts().collect { listOfFacts ->
                if(listOfFacts.isNullOrEmpty()){
                    Log.d("FactViewModel", "No facts 4 U")
                } else {
                    _facts.value = listOfFacts
                }
            }
        }
    }

    private fun getSettingsFromDB(){
        viewModelScope.launch(Dispatchers.IO) {
            _settings = settingsRepository.getSettings()
        }
    }

    fun deleteFact(fact: Fact) {
        viewModelScope.launch(Dispatchers.IO) {
            factRepository.deleteFact(fact)
        }
        getAllFactsFromDB()
    }

    @Composable
    fun getFactsFromVM(): List<Fact> {
        val facts: List<Fact> by _facts.asStateFlow().collectAsState()
        return facts
    }

    @Composable
    fun getNumOfFactsInDB() : Int {
        return getFactsFromVM().size
    }

    private val _categories = listOf("All Categories", "Sports", "History", "Movies", "Animals", "Anime & Manga", "Vehicles", "Computers")

    fun getCategoryList() : List<String>{
        return _categories
    }

    fun setCategory(index: Int){
        when(index) {
            0 -> {_settings.category = "" } //All
            1 -> {_settings.category = "21"} //Sports
            2 -> {_settings.category = "23"} //History
            3 -> {_settings.category = "11"} //Movies
            4 -> {_settings.category = "27"} //Animals
            5 -> {_settings.category = "31"} //Anime and Manga
            6 -> {_settings.category = "28"} //Vehicles
            7 -> {_settings.category = "18"} //Computers
        }
        updateSettings()
    }

    fun getSelectedCategory(): Int{
        when(_settings.category) {
            "" -> return 0
            "21" -> return 1
            "23" -> return 2
            "11" -> return 3
            "27" -> return 4
            "31" -> return 5
            "28" -> return 6
            "18" -> return 7
        }
        return 0
    }

    fun getDisplayFactAsQuestion(): Boolean? {
        return _settings.display_fact_as_question
    }

    fun setDisplayFactAsQuestion(displayFactAsQuestion: Boolean){
        _settings.display_fact_as_question = displayFactAsQuestion
        updateSettings()
    }

    private fun updateSettings(){
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.updateSettings(_settings)
        }
    }

    fun setUseSound(useSound: Boolean){
        _settings.use_sound = useSound
        updateSettings()
    }

    fun getUseSound(): Boolean? {
        return _settings.use_sound
    }

    fun setFactFrequency(frequency: Float){
        _settings.fact_frequency = frequency
        updateSettings()
    }

    fun getFactFrequency() : Float? {
        return _settings.fact_frequency
    }

    @Composable
    fun SetNotificationContext(context: Context){
        _context = context
    }

    fun getNotificationContext() : Context? {
        return _context
    }

    fun fetchNewFactTest() {
        viewModelScope.launch(Dispatchers.IO) {
            _settings.category?.let {
                _context?.let {
                        it1 -> fetchFact(category = it, repository = factRepository, context = it1) } }
        }

        _context?.let { // Notification
            simpleNotification(
                context = it,
                notificationId = 0,
                textContent = "A new Fact has arrived 4 U.",
                priority = NotificationCompat.PRIORITY_HIGH
            )
        }
    }
}