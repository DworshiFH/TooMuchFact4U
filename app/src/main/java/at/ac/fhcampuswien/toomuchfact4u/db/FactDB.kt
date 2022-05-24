package at.ac.fhcampuswien.toomuchfact4u.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import at.ac.fhcampuswien.toomuchfact4u.Fact

@Database(
    entities = [Fact::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
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
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // do work on first db creation
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            // do work on each start
                        }
                    }
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}