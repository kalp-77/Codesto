package com.example.codemaster.ui.screens.leetcode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Leetcode
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeetcodeViewModel @Inject constructor(
    val repository: Repository
) : ViewModel(){

    private val _uiState = MutableStateFlow<LeetcodeState>(LeetcodeState.Loading)
    val uiState : StateFlow<LeetcodeState> = _uiState

    init{
        viewModelScope.launch {
            repository.getLeetcodeUser().collect{
                if (it != null){
                    fetchLeetcodeData(it)
                }
            }
        }
    }

    private fun fetchLeetcodeData(username : String) = viewModelScope.launch{
        try{
            val result : Flow<Response<Leetcode>?> = repository.getLeetCodeData(username)
            result.collect{
                when(it){
                    is Response.Loading -> {
                        _uiState.value = LeetcodeState.Loading
                    }
                    is Response.Success -> {
                        _uiState.value = it.data?.let { it1 -> LeetcodeState.Success(it1) }!!
                    }
                    is Response.Failure -> {
                        _uiState.value = LeetcodeState.Failure(it.message.toString())
                    }
                    else -> {
                        _uiState.value = LeetcodeState.Loading
                    }
                }
            }
        } catch (e : Exception){
            _uiState.value = LeetcodeState.Failure("Oops! Something went wrong")
        }
    }
}