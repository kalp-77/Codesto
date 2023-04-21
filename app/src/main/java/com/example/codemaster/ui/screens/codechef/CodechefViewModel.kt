package com.example.codemaster.ui.screens.codechef

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Codechef
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CodechefViewModel @Inject constructor(
    val repository : Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CodechefState>(CodechefState.Loading)
    val uiState : StateFlow<CodechefState> = _uiState

    init{
        viewModelScope.launch {
            repository.getCodechefUser().collect {
                if(it != null) {
                    fetchCodechefData(it)
                }
            }
        }
    }
    private fun fetchCodechefData(username : String) = viewModelScope.launch {
        try {
            val result : Flow<Response<Codechef>?> = repository.getCodechefData(username)
            result.collect {
                when(it) {
                    is Response.Loading -> {
                        _uiState.value = CodechefState.Loading
                    }
                    is Response.Success -> {
                        _uiState.value = it.data?.let { it1 -> CodechefState.Success(it1) }!!
                    }
                    is Response.Failure -> {
                        _uiState.value = CodechefState.Failure(it.message.toString())
                    }

                    else -> { _uiState.value = CodechefState.Loading }
                }
            }
        } catch (ex: Exception) {
            _uiState.value = CodechefState.Failure("Oops! something went wrong")
        }
    }
}