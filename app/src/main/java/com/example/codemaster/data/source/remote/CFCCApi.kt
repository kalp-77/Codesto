package com.example.codemaster.data.source.remote

import com.example.codemaster.data.model.Codechef
import com.example.codemaster.data.model.Codeforces
import com.example.codemaster.data.model.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface CFCCApi {

    @GET("codechef/{userName}")
    suspend fun getCodechefData(
        @Path("username") username : String
    ) : Response<Codechef>?

    @GET("codeforces/{userName}")
    suspend fun getCodeforcesData(
        @Path("username") username : String
    ) : Response<Codeforces>?
}