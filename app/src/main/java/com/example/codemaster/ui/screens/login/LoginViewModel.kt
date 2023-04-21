package com.example.codemaster.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.source.repository.Repository
import com.example.codemaster.navigation.Screens
import com.example.codemaster.utils.NavigateUI
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: Repository
) : ViewModel() {

    /** Hot Flows **/
    private var _uiEvents = Channel<NavigateUI>()
    val uiEvents = _uiEvents.receiveAsFlow()

    /** Cold Flows **/
    private var _userDetails = MutableStateFlow<Response<FirebaseUser>?>(null)
    val userDetails : StateFlow<Response<FirebaseUser>?> = _userDetails.asStateFlow()

    private val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    init {
        if(currentUser != null) {
            onEvent(NavigateUI.Navigate(onNavigate = Screens.ContestsScreen))
        }
    }

    fun loginUser(email : String, password: String) = viewModelScope.launch{
        val authData = authRepository.loginUser(email, password)
        authData.collect {result->
            when(result){
                is Response.Loading<*> -> {
                    _userDetails.value = Response.Loading()
                }
                is Response.Success<*> -> {
                    _userDetails.value = Response.Success(result.data?.user)
                    onEvent(NavigateUI.Navigate(onNavigate = Screens.ContestsScreen))
                }
                is Response.Failure<*> -> {
                    _userDetails.value = Response.Failure(result.message.toString())
                }
            }
        }
    }
    fun logout() = viewModelScope.launch {
        authRepository.logout()
        onEvent(NavigateUI.Navigate(onNavigate = Screens.LoginScreen))
    }

    fun onEvent(event: NavigateUI) = viewModelScope.launch {
        _uiEvents.send(event)
    }
}