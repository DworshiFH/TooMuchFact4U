package at.ac.fhcampuswien.toomuchfact4u.api

import android.util.Log
import at.ac.fhcampuswien.toomuchfact4u.Fact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FactsAPI {


    @GET("api.php?amount=1&{category}")
    suspend fun getFactFromCategory(
        @Path("category") category: String
    ) : List<Fact>

    @GET("api.php?amount=10")
    suspend fun getRandomFact() : Response<List<Fact>>

    /*companion object {
        var apiService: FactsAPI? = null
        fun getInstance() : FactsAPI {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://opentdb.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(FactsAPI::class.java)
            }



            return apiService!!
        }
    }*/
}

/*
"https://opentdb.com/api.php?amount=1" //All
"https://opentdb.com/api.php?amount=1&category=21" //Sports
"https://opentdb.com/api.php?amount=1&category=23" //History
 */