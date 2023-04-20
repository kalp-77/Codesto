package com.example.codemaster.di.module

import com.example.codemaster.data.source.remote.CFCCApi
import com.example.codemaster.data.source.remote.CodeforcesOfficialApi
import com.example.codemaster.data.source.remote.ContestApi
import com.example.codemaster.data.source.remote.LeetcodeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesLeetcode(@Named("Leetcode") retrofit: Retrofit) : LeetcodeApi {
        return retrofit.create(LeetcodeApi::class.java)
    }

    @Provides
    fun providesCFCC(@Named("CFCC") retrofit: Retrofit) : CFCCApi {
        return retrofit.create(CFCCApi::class.java)
    }

    @Provides
    fun providesCodeforces(@Named("Codeforces") retrofit : Retrofit) : CodeforcesOfficialApi {
        return retrofit.create(CodeforcesOfficialApi::class.java)
    }

    @Provides
    fun provideContestDetails(@Named("ContestDetails") retrofit: Retrofit) : ContestApi {
        return retrofit.create(ContestApi::class.java)
    }

}