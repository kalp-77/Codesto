package com.example.codemaster.ui.screens.platform

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlatformViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _platformState = MutableStateFlow<Response<String>>(Response.Loading())
    val platformState = _platformState.asStateFlow()

    private val _username = mutableStateOf("")
    val username = _username

    private val _codechefUser = mutableStateOf("")
    val codechefUser = _codechefUser

    private val _codeforcesUser = mutableStateOf("")
    val codeforcesUser = _codeforcesUser

    private val _leetcodeUser = mutableStateOf("")
    val leetcodeUser = _leetcodeUser

    private val _uiEvents = Channel<NavigateUI>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        viewModelScope.launch {
            getUsername()
            getCodechefUser()
            getCodeforcesUser()
            getLeetcodeUser()

        }
    }
    suspend fun saveUsernames(username: String, cfUsername: String, ccUsername: String, lcUsername: String) = viewModelScope.launch {
        val res = repository.savePlatformUser(
            ccUsername = ccUsername,
            username = username,
            cfUsername = cfUsername,
            lcUsername = lcUsername
        )
        res.collect {
            when (it) {
                is Response.Loading<*> -> {
                    _platformState.value = Response.Loading()
                }
                is Response.Success<*> -> {
                    _platformState.value = Response.Success(data = "username saved")
                }
                is Response.Failure<*> -> {
                    _platformState.value = Response.Failure(message = it.message.toString())
                }
            }
        }
    }
    private suspend fun getUsername() = viewModelScope.launch {
        val username = repository.getUsername()
        username.collect {
            if(it != null) {
                _username.value = it.toString()
            }
        }
    }
    private suspend fun getCodechefUser() = viewModelScope.launch {
        repository.getCodechefUser().collect {
            _codechefUser.value = it.toString()
        }
    }
    private suspend fun getCodeforcesUser() = viewModelScope.launch {
        repository.getCodeforcesUser().collect {
            _codeforcesUser.value = it.toString()
        }
    }
    private suspend fun getLeetcodeUser() = viewModelScope.launch {
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

