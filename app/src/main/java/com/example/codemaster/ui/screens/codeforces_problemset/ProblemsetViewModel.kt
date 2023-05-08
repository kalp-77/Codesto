package com.example.codemaster.ui.screens.codeforces_problemset

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.model.codeforces_model.Problem
import com.example.codemaster.data.source.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProblemsetViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProblemsetState>(ProblemsetState.Empty)
    val uiState: StateFlow<ProblemsetState> = _uiState

    private val _uiDataState = mutableStateOf<List<Problem>>(emptyList())


    private val tags = ArrayList<String>()
    init{
        fetchProblems(tags)
    }
    fun fetchProblems(tags: ArrayList<String>) {
        _uiState.value = ProblemsetState.Loading
        viewModelScope.launch {
            val tag = mutableStateOf("")
            for(items in tags){
                tag.value += "$items;"
            }
            try {
                val result = repository.getCodeforcesProblemset(tag.value)
                result.collect {
                    when (it) {
                        is Response.Loading -> {
                            _uiState.value = ProblemsetState.Loading
                        }
                        is Response.Success -> {
                            _uiDataState.value = it.data?.result?.problems!!
                            _uiState.value = ProblemsetState.Success(data = it.data.result.problems)
                        }
                        is Response.Failure -> {
                            _uiState.value = ProblemsetState.Failure(it.message.toString())
                        }
                        else -> {}
                    }
                }
            }
            catch (ex: Exception) {
                _uiState.value = ProblemsetState.Failure(message = ex.message.toString())
            }
        }
    }
    fun filterData(rating: Int) {
        val filterData = _uiDataState.value.filter {
            it.rating == rating
        }
        _uiState.value = ProblemsetState.Success(data = filterData)
    }
}