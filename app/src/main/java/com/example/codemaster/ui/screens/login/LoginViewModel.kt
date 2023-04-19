package com.example.codemaster.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: Repository
) : ViewModel() {

    // Hot Channel flow
    val _loginState = Channel<LoginState>()
    val loginState = _loginState.receiveAsFlow()

    // Cold State flow
    private var _userDetails = MutableStateFlow<Response<FirebaseUser>?>(null)
    val userDetails : StateFlow<Response<FirebaseUser>?> = _userDetails

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    init {
        if(authRepository.currentUser != null) {
            viewModelScope.launch {
                _loginState.send(LoginState(isSuccess = "already logged in"))
                _userDetails.value = Response.Success(authRepository.currentUser)
            }
        }
    }
    fun loginUser(email : String, password: String) = viewModelScope.launch{
        val authData = authRepository.loginUser(email, password)
        authData.collect {result->
            when(result){
                is Response.Loading<*> -> {
                    _loginState.send(LoginState(isLoading = true))
                    _userDetails.value = Response.Loading()
                }
                is Response.Success<*> -> {
                    _loginState.send(LoginState(isSuccess = "SuccessFully Logged in"))
                    _userDetails.value = Response.Success(result.data?.user)
                }
                is Response.Failure<*> -> {
                    _loginState.send(LoginState(isFailure = result.message))
                    _userDetails.value = Response.Failure(result.message.toString())
                }
            }
        }
    }
    fun logout() = viewModelScope.launch {
        authRepository.logout()
    }
}