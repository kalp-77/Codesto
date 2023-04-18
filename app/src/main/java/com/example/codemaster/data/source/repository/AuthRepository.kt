package com.example.codemaster.data.source.repository

import com.example.codemaster.data.model.Response
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun loginUser(email: String, password: String) : Flow<Response<AuthResult>>
    fun signupUser(email: String, password: String) : Flow<Response<AuthResult>>
}