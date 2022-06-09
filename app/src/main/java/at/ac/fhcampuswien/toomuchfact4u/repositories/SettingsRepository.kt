package at.ac.fhcampuswien.toomuchfact4u.repositories

import at.ac.fhcampuswien.toomuchfact4u.dataModels.Settings
import at.ac.fhcampuswien.toomuchfact4u.db.SettingsDao

class SettingsRepository(private val dao: SettingsDao) {

    suspend fun updateSettings(settings: Settings) = dao.updateSettings(settings = settings)

    suspend fun getSettings() = dao.getSettingsById(id = 1L)
}