package at.ac.fhcampuswien.toomuchfact4u.repositories

import at.ac.fhcampuswien.toomuchfact4u.Fact
import at.ac.fhcampuswien.toomuchfact4u.db.FactDao
import kotlinx.coroutines.flow.Flow

class FactRepository(private val dao: FactDao) {

    suspend fun addFact(fact: Fact) = dao.addFact(fact = fact)

    suspend fun deleteFact(fact: Fact) = dao.deleteFact(fact = fact)

    fun getAllFacts(): Flow<List<Fact>> = dao.getAllFacts()

    fun getFactCount(): Int = dao.getFactCount()
}