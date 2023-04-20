package com.example.codemaster.data.source.remote

import com.example.codemaster.data.model.Leetcode
import com.example.codemaster.data.model.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface LeetcodeApi {
    @GET("/{username}")
    suspend fun getLeetcodeData(
        @Path("username") username : String
    ) : Flow<Response<Leetcode>>
}