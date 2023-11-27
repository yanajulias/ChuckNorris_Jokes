package test.app.cnjokes.data

import retrofit2.http.GET
import retrofit2.http.Query

interface JokesApi {
    @GET("jokes/search")
    suspend fun getQueryJokesList(@Query("query") query: String) : JokesResponse
}