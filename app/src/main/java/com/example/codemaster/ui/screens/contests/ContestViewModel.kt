package com.example.codemaster.ui.screens.contests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Contest
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContestViewModel @Inject constructor(
    val repository: Repository
): ViewModel(){

    private val _uiState = MutableStateFlow<ContestState>(ContestState.Loading)
    val uiState : StateFlow<ContestState> = _uiState

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
        try{
            val result: Flow<Response<Contest>?> = repository.getAllContestData()
            result.collect{
                when(it){
                    is Response.Loading -> {
                        _uiState.value = ContestState.Loading
                    }
                    is Response.Success -> {
                        _uiState.value = it.data?.let { it1 -> ContestState.Success(it1) }!!
                    }
                    is Response.Failure -> {
                    _uiState.value = ContestState.Failure(it.message.toString())
                    }
                    else ->{
                        _uiState.value = ContestState.Loading
                    }
                }
            }
        }
        catch (e: Exception){
            _uiState.value = ContestState.Failure("Oops! Something Went wrong")
        }
    }
}