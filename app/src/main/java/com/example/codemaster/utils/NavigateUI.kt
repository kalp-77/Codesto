package com.example.codemaster.utils

import com.example.codemaster.navigation.Screens

sealed class NavigateUI{
    object PopBackStack: NavigateUI()
    data class Navigate(val onNavigate: Screens): NavigateUI()
    data class Snackbar(val message: String) : NavigateUI()
}
