package com.example.codemaster.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: Repository
) : ViewModel() {

    val _signupState = Channel<SignupState>()
    val signupState = _signupState.receiveAsFlow()

    fun signupUser(name: String, email : String, password: String) = viewModelScope.launch{
        authRepository.signupUser(name, email, password).collect {result->
            when(result){
                is Response.Loading<*> -> {
                    _signupState.send(SignupState(isLoading = true))
                }
                is Response.Success<*> -> {
                    _signupState.send(SignupState(isSuccess = "User Registered"))
                }
                is Response.Failure<*> -> {
                    _signupState.send(SignupState(isFailure = result.message))
                }

            }
        }
    }
}