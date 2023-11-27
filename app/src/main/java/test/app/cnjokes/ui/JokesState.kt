package test.app.cnjokes.ui

import test.app.cnjokes.data.JokesResponse

data class JokesState(
    val data: List<JokesResponse.Result> = emptyList(),
    val isLoading : Boolean = false,
    val errorMessage: String = ""
)