package at.ac.fhcampuswien.toomuchfact4u.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class FactViewModelFactory(private val repository: FactRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FactViewModel::class.java)){
            return FactViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}