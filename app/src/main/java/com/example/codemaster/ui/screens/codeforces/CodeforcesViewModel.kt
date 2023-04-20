package com.example.codemaster.ui.screens.codeforces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CodeforcesViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CodeforcesState>(CodeforcesState.Loading)
    val uiState : StateFlow<CodeforcesState> = _uiState

    // handle events send by the ui layer
    private val _uiEvents = Channel<CodeforcesUiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun processEvent(event : CodeforcesUiEvents) = viewModelScope.launch {
        when(event){
            is CodeforcesUiEvents.onProblemsClick -> {
                _uiState.value = CodeforcesState.Navigate(screen = "Rating_Change_Screen")
            }
            is CodeforcesUiEvents.onRatingClick -> {
                _uiState.value = CodeforcesState.Navigate(screen = "Problemset_Screen")
            }
        }
    }

    fun fetchCodeforcesData(username : String) = viewModelScope.launch {
        try{
            val ratingGraphData = repository.getCodeforcesScreenData(username).collect {
                when(it) {
                    is Response.Loading -> {
                        _uiState.value = CodeforcesState.Loading
                    }
                    is Response.Success -> {
                        _uiState.value = it.data?.let { it1 -> CodeforcesState.Success(data = it1) }!!
                    }
                    is Response.Failure -> {
                        _uiState.value = CodeforcesState.Failure(it.message.toString())
                    }

                    else -> {}
                }
            }
        }
        catch (e:Exception) {
            _uiState.value = CodeforcesState.Failure("Oops! Something went wrong")
        }
    }

}