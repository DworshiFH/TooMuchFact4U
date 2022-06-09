package at.ac.fhcampuswien.toomuchfact4u.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import at.ac.fhcampuswien.toomuchfact4u.dataModels.Fact

@Dao
interface FactDao {

    @Insert
    suspend fun addFact(fact: Fact)

    @Delete
    suspend fun deleteFact(fact: Fact)

    @Query("SELECT * from facts")
    fun getAllFacts(): Flow<List<Fact>>

    @Query("SELECT count(*) from facts")
    fun getFactCount(): Int

    /*@Query("DELETE from facts where id=:id")
    suspend fun deleteNoteById(id: Long): Fact*/
}