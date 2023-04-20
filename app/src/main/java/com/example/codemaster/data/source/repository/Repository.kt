package com.example.codemaster.data.source.repository

import com.example.codemaster.data.model.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface Repository {

    // Firebase Authentication
    val currentUser: FirebaseUser?
    suspend fun loginUser(email: String, password: String) : Flow<Response<AuthResult>>
    suspend fun signupUser(name: String, email: String, password: String) : Flow<Response<AuthResult>>
    fun logout()


    // Firebase database
    suspend fun saveCodechefUser(username: String?) : Flow<Response<String>>
    suspend fun saveCodeforcesUser(username: String?) : Flow<Response<String>>
    suspend fun saveLeetcodeUser(username: String?) : Flow<Response<String>>


    fun getCodechefUser() : Flow<String?>
    fun getCodeforcesUser() : Flow<String?>
    fun getLeetcodeUser() : Flow<String?>

    // test change
    // test change 2
}