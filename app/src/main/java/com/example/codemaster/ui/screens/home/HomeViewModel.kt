package com.example.codemaster.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Contest
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import com.example.codemaster.utils.NavigateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: Repository
): ViewModel(){

    private val _uiState = MutableStateFlow<HomeState>(HomeState.Loading)
    val uiState : StateFlow<HomeState> = _uiState

    private val _uiState2 = MutableStateFlow<HomeState>(HomeState.Loading)
    val uiState2 : StateFlow<HomeState> = _uiState2

    private val _uiState3 = MutableStateFlow<HomeState>(HomeState.Loading)
    val uiState3 : StateFlow<HomeState> = _uiState3

    init{
        viewModelScope.launch {
            repository.getAllContestData().collect{
                if(it != null){
                    fetchContestsData()
                }
            }
        }
    }

    private fun fetchContestsData() = viewModelScope.launch {
        try {
            val result: Flow<Response<Contest>?> = repository.getAllContestData()
            result.collect {
                when (it) {
                    is Response.Loading -> {
                        _uiState.value = HomeState.Loading
                        _uiState2.value = HomeState.Loading
                        _uiState3.value = HomeState.Loading

                    }

                    is Response.Success -> {
                        _uiState.value = it.data?.let { it1 -> HomeState.Success(it1.filter { it.status == "CODING" } ) }!!
                        _uiState2.value = it.data.let { it1 -> HomeState.Success(it1.filter { it.in_24_hours == "Yes" }) }
                        _uiState3.value = it.data.let { it1 -> HomeState.Success(it1.filter { it.status == "BEFORE" }) }

                    }

                    is Response.Failure -> {
                        _uiState.value = HomeState.Failure(it.message.toString())
                        _uiState2.value = HomeState.Failure(it.message.toString())
                        _uiState3.value = HomeState.Failure(it.message.toString())

                    }

                    else -> {
                        _uiState.value = HomeState.Loading
                        _uiState2.value = HomeState.Loading
                        _uiState3.value = HomeState.Loading

                    }

                }
            }
        }
        catch (e: Exception){
            _uiState.value = HomeState.Failure("Oops! Something Went wrong")
            _uiState2.value = HomeState.Failure("Oops! Something Went wrong")
            _uiState3.value = HomeState.Failure("Oops! Something Went wrong")

        }
    }
}