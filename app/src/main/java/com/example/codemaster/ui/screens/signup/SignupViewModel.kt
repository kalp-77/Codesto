package com.example.codemaster.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import com.example.codemaster.navigation.Screens
import com.example.codemaster.utils.NavigateUI
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: Repository
) : ViewModel() {

    private var _uiEvents = Channel<NavigateUI>()
    val uiEvents = _uiEvents.receiveAsFlow()

    val _signupState = Channel<Response<FirebaseUser>?>()
    val signupState = _signupState.receiveAsFlow()

    fun signupUser(name: String, email : String, password: String) = viewModelScope.launch{
        authRepository.signupUser(name, email, password).collect {result->
            when(result){
                is Response.Loading<*> -> {
                    _signupState.send(Response.Loading())
                }
                is Response.Success<*> -> {
                    _signupState.send(Response.Success(result.data?.user))
                    onEvent(NavigateUI.Navigate(Screens.PlatformScreen))
                }
                is Response.Failure<*> -> {
                    _signupState.send(Response.Failure(result.message.toString()))
                }
            }
        }
    }

    fun onEvent(event: NavigateUI) = viewModelScope.launch {
        _uiEvents.send(event)
    }
}