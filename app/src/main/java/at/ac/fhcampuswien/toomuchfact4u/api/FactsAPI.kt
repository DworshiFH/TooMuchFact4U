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
    ) : Response<NestedJSONModel>

    @GET("api.php?amount=1")
    suspend fun getRandomFact() : Response<NestedJSONModel>
}