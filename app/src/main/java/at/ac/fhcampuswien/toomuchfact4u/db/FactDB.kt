package at.ac.fhcampuswien.toomuchfact4u.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import at.ac.fhcampuswien.toomuchfact4u.dataModels.Fact

@Database(
    entities = [Fact::class],
    version = 1,
    exportSchema = false
)
abstract class FactDB: RoomDatabase() {
    abstract fun factDao(): FactDao

    companion object {
        @Volatile
        private var INSTANCE: FactDB? = null

        fun getDatabase(context: Context): FactDB {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): FactDB {
            return Room
                .databaseBuilder(context, FactDB::class.java, "facts_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}