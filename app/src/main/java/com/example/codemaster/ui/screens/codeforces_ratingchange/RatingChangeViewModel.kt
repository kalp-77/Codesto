package com.example.codemaster.ui.screens.codeforces_ratingchange

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import com.example.codemaster.ui.screens.codeforces_problemset.ProblemsetState
import com.example.codemaster.utils.NavigateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingChangeViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RatingChangeState>(RatingChangeState.Empty)
    val uistate: StateFlow<RatingChangeState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<NavigateUI>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init{
        viewModelScope.launch {
            repository.getCodeforcesUser().collect{
                if(it!=null){
                    fetchRatingChanges(it)
                }
                else {
                    _uiState.value = RatingChangeState.Failure("please provide username")
                }
            }
        }
    }
    private fun fetchRatingChanges(username: String) {
        _uiState.value = RatingChangeState.Loading
        viewModelScope.launch {
            try {
                val result = repository.getCodeforcesUserRatingChange(username = username)
                result.collect {
                    when (it) {
                        is Response.Loading -> {
                            _uiState.value = RatingChangeState.Loading
                        }

                        is Response.Success -> {
                            val data = it.data?.result?.reversed()
                            _uiState.value = RatingChangeState.Success(data = data!!)
                        }
                        is Response.Failure -> {
                            _uiState.value = RatingChangeState.Failure(it.message.toString())
                        }
                        else -> {}
                    }
                }
            }
            catch (ex: Exception) {
                _uiState.value = RatingChangeState.Failure(message = ex.message.toString())
            }
        }
    }
    fun onEvent(event : NavigateUI) = viewModelScope.launch {
        _uiEvents.send(event)
    }
}