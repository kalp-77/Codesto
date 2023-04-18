package com.example.codemaster.data.source.repository

import com.example.codemaster.data.model.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


// provide AuthRepositoryImpl inside app module
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Response.Success(result))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }

    override fun signupUser(email: String, password: String): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Response.Success(result))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }
}