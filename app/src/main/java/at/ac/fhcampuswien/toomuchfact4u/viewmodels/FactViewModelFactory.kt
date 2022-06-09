package at.ac.fhcampuswien.toomuchfact4u.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.ac.fhcampuswien.toomuchfact4u.repositories.FactRepository
import at.ac.fhcampuswien.toomuchfact4u.repositories.SettingsRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class FactViewModelFactory(
    private val factRepository: FactRepository,
    private val settingsRepository: SettingsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FactViewModel::class.java)){
            return FactViewModel(factRepository = factRepository,
                settingsRepository = settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}