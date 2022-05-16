package at.ac.fhcampuswien.toomuchfact4u.api

import retrofit2.*
import retrofit2.http.GET
import retrofit2.http.Path

interface FactsAPI {
    @GET("api.php?amount=1&{category}")
    suspend fun getFactFromCategory(
        @Path("category") category: String
    ) : Response<FactJSONModel>

    @GET("api.php?amount=1")
    suspend fun getRandomFact() : Response<FactJSONModel>
}