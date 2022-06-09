package at.ac.fhcampuswien.toomuchfact4u.api

import at.ac.fhcampuswien.toomuchfact4u.dataModels.FactJSONModel
import retrofit2.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.URL

interface FactsAPI {

    @GET("api.php?amount=1")
    suspend fun getFactFromCategory(@Query("category") category: String): Response<FactJSONModel>

    @GET("api.php?amount=1")
    suspend fun getRandomFact() : Response<FactJSONModel>
}