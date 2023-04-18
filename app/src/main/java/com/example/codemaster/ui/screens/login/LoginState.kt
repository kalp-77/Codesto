package com.example.codemaster.ui.screens.login

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isFailure: String? = ""
)
