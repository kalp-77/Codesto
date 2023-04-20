package com.example.codemaster.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codemaster.data.source.repository.Repository
import com.example.codemaster.navigation.Screens
import com.example.codemaster.utils.NavigateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: Repository
) : ViewModel(){

    // handle events send by the ui layer
    private val _uiEvents = Channel<NavigateUI>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onEvent(event: NavigateUI) = viewModelScope.launch {
        _uiEvents.send(event)
    }
    fun logout() = viewModelScope.launch {
        repository.logout()
        onEvent(NavigateUI.Navigate(onNavigate = Screens.LoginScreen))
    }
}