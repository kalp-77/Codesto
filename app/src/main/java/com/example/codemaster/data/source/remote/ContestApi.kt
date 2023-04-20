package com.example.codemaster.data.source.remote

import com.example.codemaster.data.model.Contest
import kotlinx.coroutines.flow.Flow
import com.example.codemaster.data.model.Response
import retrofit2.http.GET

interface ContestApi {
    @GET("api/v1/all")
    suspend fun getContestDetailsData() : Response<Contest>?
}