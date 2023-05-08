package com.example.codemaster.data.source.remote

import com.example.codemaster.data.model.Codechef
import com.example.codemaster.data.model.Codeforces
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CFCCApi {

    @GET("codechef/{username}")
    suspend fun getCodechefData(
        @Path("username") username : String
    ) : Response<Codechef>?

    @GET("codeforces/{username}")
    suspend fun getCodeforcesData(
        @Path("username") username : String
    ) : Response<Codeforces>?
}