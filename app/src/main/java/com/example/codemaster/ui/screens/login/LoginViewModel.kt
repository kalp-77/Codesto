package com.example.codemaster.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val _loginState = Channel<LoginState>()
    val loginState = _loginState.receiveAsFlow()

    fun loginUser(email : String, password: String) = viewModelScope.launch{
        authRepository.loginUser(email, password).collect {result->
            when(result){
                is Response.Loading<*> -> {
                    _loginState.send(LoginState(isLoading = true))
                }
                is Response.Success<*> -> {
                    _loginState.send(LoginState(isSuccess = "Login Success"))
                }
                is Response.Failure<*> -> {
                    _loginState.send(LoginState(isFailure = result.message))
                }
            }
        }
    }
}