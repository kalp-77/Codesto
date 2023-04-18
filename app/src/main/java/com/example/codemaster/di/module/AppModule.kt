package com.example.codemaster.di.module

import com.example.codemaster.data.source.repository.AuthRepository
import com.example.codemaster.data.source.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) //install this module inside singleton component class
// this object will live in singleton component class as long as our application lives
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()  // we can use this instance in firebase AuthRepository

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(firebaseAuth: FirebaseAuth) :AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)  // we can use this repository implementation in view model
    }

}