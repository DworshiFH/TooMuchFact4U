package at.ac.fhcampuswien.toomuchfact4u.viewmodels

import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.toomuchfact4u.Fact

class FactViewModel : ViewModel() {
    private val _facts = mutableListOf<Fact>()

    fun getFacts() : List<Fact>{
        return _facts
    }
    
}