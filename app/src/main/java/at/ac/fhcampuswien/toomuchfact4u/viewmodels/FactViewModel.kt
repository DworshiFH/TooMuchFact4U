package at.ac.fhcampuswien.toomuchfact4u.viewmodels

import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.toomuchfact4u.Fact

class FactViewModel : ViewModel() {
    private val _facts = mutableListOf<Fact>()

    fun getFacts() : List<Fact>{
        return _facts
    }



    private val _categories = listOf("All Categories", "Sports", "History")
    fun getCategoryList() : List<String>{
        return _categories
    }
    fun setCategory(index: Int){
        when(index) {
            0 -> setURL("https://opentdb.com/api.php?amount=1&type=multiple") //All
            1 -> setURL("https://opentdb.com/api.php?amount=1&category=21&type=multiple") //Sports
            2 -> setURL("https://opentdb.com/api.php?amount=1&category=23&type=multiple") //History
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