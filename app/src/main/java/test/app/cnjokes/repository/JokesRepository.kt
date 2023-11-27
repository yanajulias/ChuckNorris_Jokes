package test.app.cnjokes.repository

import test.app.cnjokes.data.JokesApi
import test.app.cnjokes.data.JokesResponse
import test.app.cnjokes.util.Resource
import javax.inject.Inject

class JokesRepository @Inject constructor(
    private val jokesApi: JokesApi
) {
    suspend fun getQueryItems(query: String): Resource<JokesResponse> {
        return try {
            val result = jokesApi.getQueryJokesList(query = query)
            Resource.Success(data = result)
        } catch (e: Exception) {
            Resource.Failure(message = e.message.toString())
        }
    }
}