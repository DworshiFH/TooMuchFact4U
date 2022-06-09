package at.ac.fhcampuswien.toomuchfact4u.db

import androidx.room.*
import at.ac.fhcampuswien.toomuchfact4u.dataModels.Settings

@Dao
interface SettingsDao {
    @Insert
    suspend fun insertSettings(settings: Settings)

    @Update
    suspend fun updateSettings(settings: Settings)

    @Query("SELECT * from settings where id=:id")
    suspend fun getSettingsById(id: Long): Settings
}