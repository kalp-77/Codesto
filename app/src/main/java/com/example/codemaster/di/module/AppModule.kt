package com.example.codemaster.di.module

import android.app.Application
import android.content.Context
import com.example.codemaster.data.source.repository.Repository
import com.example.codemaster.data.source.repository.RepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()  // we can use this instance in firebase AuthRepository


    @Provides
    @Singleton
    fun provideRealtimeDatabase() : DatabaseReference = Firebase.database.reference.child("user")

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(
        firebaseAuth: FirebaseAuth,
        db : DatabaseReference
    ) : Repository {
        return RepositoryImpl(firebaseAuth, db)  // we can use this repository implementation in view model
    }

}