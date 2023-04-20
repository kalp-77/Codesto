package com.example.codemaster.data.source.remote

import com.example.codemaster.data.model.codeforces_model.CodeforcesProblemset
import com.example.codemaster.data.model.codeforces_model.UserInfo
import com.example.codemaster.data.model.codeforces_model.UserRatingChanges
import kotlinx.coroutines.flow.Flow
import com.example.codemaster.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CodeforcesOfficialApi {

    @GET("user.rating?")
    suspend fun getUserRatingChanges(
        @Query("handle") handle : String
    ) : Response<UserRatingChanges>?

    @GET("problemset.problems?")
    suspend fun getUserProblemset(
        @Query("tags", encoded = true) tags : String
    ) : Response<CodeforcesProblemset>?

    @GET("user.info?")
    suspend fun getCodeforcesUserInfo(
        @Query("handles") handle : String
    ) : Response<UserInfo>?
}