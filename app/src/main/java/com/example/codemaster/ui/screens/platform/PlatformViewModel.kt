package com.example.codemaster.ui.screens.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import com.example.codemaster.utils.NavigateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlatformViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    val _platformState = Channel<PlatformState>()
    val platformState = _platformState.receiveAsFlow()

    private val _codechefUser = MutableStateFlow("")
    val codechefUser = _codechefUser.asStateFlow()

    private val _codeforcesUser = MutableStateFlow("")
    val codeforcesUser = _codeforcesUser.asStateFlow()

    private val _leetcodeUser = MutableStateFlow("")
    val leetcodeUser = _leetcodeUser.asStateFlow()

    private val _uiEvents = Channel<NavigateUI>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        viewModelScope.launch {
            getCodechefUser()
            getCodeforcesUser()
            getLeetcodeUser()
        }
    }

    suspend fun saveUserName(platform:String, username: String) = viewModelScope.launch {
        if(platform == "codechef"){
            val result: Flow<Response<String>> = repository.saveCodechefUser(username = username)
            result.collect {
                when (it) {
                    is Response.Loading<*> -> {
                        _platformState.send(PlatformState(isLoading = true))
                    }
                    is Response.Success<*> -> {
                        _platformState.send(PlatformState(isSuccess = "username saved"))
                    }
                    is Response.Failure<*> -> {
                        _platformState.send(PlatformState(isFailure = it.message))
                    }
                }
            }
        }
        if(platform == "codeforces"){
            repository.saveCodeforcesUser(username = username).collect {
                when (it) {
                    is Response.Loading<*> -> {
                        _platformState.send(PlatformState(isLoading = true))
                    }
                    is Response.Success<*> -> {
                        _platformState.send(PlatformState(isSuccess = "username saved"))
                    }
                    is Response.Failure<*> -> {
                        _platformState.send(PlatformState(isFailure = it.message))
                    }
                }
            }
        }
        if(platform == "leetcode"){
            repository.saveLeetcodeUser(username = username).collect {
                when (it) {
                    is Response.Loading<*> -> {
                        _platformState.send(PlatformState(isLoading = true))
                    }
                    is Response.Success<*> -> {
                        _platformState.send(PlatformState(isSuccess = "username saved"))
                    }
                    is Response.Failure<*> -> {
                        _platformState.send(PlatformState(isFailure = it.message))
                    }
                }
            }
        }
    }
    suspend fun getCodechefUser() = viewModelScope.launch {
        repository.getCodechefUser().collect {
            _codechefUser.value = it.toString()
        }
    }
    suspend fun getCodeforcesUser() = viewModelScope.launch {
        repository.getCodeforcesUser().collect {
            _codeforcesUser.value = it.toString()
        }
    }
    suspend fun getLeetcodeUser() = viewModelScope.launch {
        // repository will send flow of data and viewmodel will collect it and update its state
        val data = repository.getLeetcodeUser()
        data.collect {
            // data is collected at viewmodel and state is updated
            _leetcodeUser.value = it.toString()
        }
    }

    fun onEvent(event : NavigateUI) = viewModelScope.launch {
        _uiEvents.send(event)
    }
}

