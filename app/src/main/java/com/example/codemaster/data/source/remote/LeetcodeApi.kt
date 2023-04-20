package com.example.codemaster.data.source.remote

import com.example.codemaster.data.model.Leetcode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LeetcodeApi {
    @GET("/{username}")
    suspend fun getLeetCodeData(
        @Path("username") username : String
    ) : Response<Leetcode>?
}