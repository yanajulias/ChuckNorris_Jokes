package test.app.cnjokes.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import test.app.cnjokes.repository.JokesRepository
import test.app.cnjokes.util.Resource
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepository: JokesRepository
) : ViewModel() {
    val list: MutableState<JokesState> = mutableStateOf(JokesState())

    fun getJokesList(query: String) {
        viewModelScope.launch {
            list.value = JokesState(isLoading = true)
            try {
                when (val result = jokesRepository.getQueryItems(query)) {
                    is Resource.Success -> {
                        result.data?.result?.let {
                            list.value = JokesState(data = it)
                        }
                    }

                    is Resource.Failure -> {
                        list.value = JokesState(errorMessage = "Size must be between 3 and 120")
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                list.value = JokesState(errorMessage = "Something went wrong.t")
            }
        }
    }
}