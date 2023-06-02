package com.example.codemaster.data.source.remote

import com.example.codemaster.data.model.codeforces_model.problemset.CodeforcesProblemset
import com.example.codemaster.data.model.codeforces_model.user_info.UserInfo
import com.example.codemaster.data.model.codeforces_model.rating_change.UserRatingChanges
import com.example.codemaster.data.model.codeforces_model.problem_solved.SolvedProblems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CodeforcesOfficialApi {


    @GET("user.rating?")
    suspend fun getUserRatingChanges(
        @Query("handle", encoded = true) handle : String
    ) : Response<UserRatingChanges>?

    @GET("problemset.problems?")
    suspend fun getUserProblemset(
        @Query("tags", encoded = true) tags : String
    ) : Response<CodeforcesProblemset>?

    @GET("user.info?")
    suspend fun getCodeforcesUserInfo(
        @Query("handles", encoded = true) handle : String
    ) : Response<UserInfo>?

    @GET("user.status?")
    suspend fun getUserProblemSolvedStatus(
        @Query("handle", encoded = true) handle : String
    ) : Response<SolvedProblems>


}