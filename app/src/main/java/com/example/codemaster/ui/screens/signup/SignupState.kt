package com.example.codemaster.ui.screens.signup

data class SignupState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isFailure: String? = ""
)
