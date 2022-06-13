package at.ac.fhcampuswien.toomuchfact4u.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import at.ac.fhcampuswien.toomuchfact4u.dataModels.Settings
import kotlinx.coroutines.*

@Database(
    entities = [Settings::class],
    version = 1,
    exportSchema = true
)

abstract class SettingsDB: RoomDatabase() {
    abstract fun settingsDao(): SettingsDao

    companion object{
        @Volatile
        private var INSTANCE: SettingsDB? = null

        fun getDatabase(context: Context): SettingsDB {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): SettingsDB{
            return Room
                .databaseBuilder(context, SettingsDB::class.java, "settings_database")
                .addCallback(
                    object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //upon db creation, create the one and only entry of the db with default values
                            val settings = Settings(
                                id = 1L,
                                fact_frequency = 5f,
                                use_sound = false,
                                display_fact_as_question = true,
                                category = "") // default category 'ALL' is empty string
                            val scope = CoroutineScope(Dispatchers.IO)
                            scope.launch {
                                getDatabase(context).settingsDao().insertSettings(settings = settings)
                            }
                        }
                    }
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}