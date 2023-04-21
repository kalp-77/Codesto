package com.example.codemaster.di.module

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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

    @Singleton
    val gson = GsonBuilder()
        .setLenient()
        .create()!!

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .build()
            chain.proceed(request)
        }
        .build()

    @Provides
    @Singleton
    @Named("Leetcode")
    fun providesLeetcodeApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://leetcode-api-five.vercel.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    @Named("CFCC")
    fun providesCfCcApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://contest-api.vercel.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    @Provides
    @Singleton
    @Named("Codeforces")
    fun providesCodeforcesApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://codeforces.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @Named("ContestDetails")
    fun providesContestDetailsApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://kontests.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}