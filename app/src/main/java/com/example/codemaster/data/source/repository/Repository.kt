package com.example.codemaster.data.source.repository

import com.example.codemaster.data.model.Codechef
import com.example.codemaster.data.model.Contest
import com.example.codemaster.data.model.Leetcode
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.model.codeforces_model.CodeforcesProblemset
import com.example.codemaster.data.model.codeforces_model.UserRatingChanges
import com.example.codemaster.ui.screens.codeforces.CodeforcesScreenData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface Repository {

    /** Firebase Authentication **/
    val currentUser: FirebaseUser?
    suspend fun loginUser(email: String, password: String) : Flow<Response<AuthResult>>
    suspend fun signupUser(name: String, email: String, password: String) : Flow<Response<AuthResult>>
    fun logout()


    /**  Firebase database **/
    suspend fun saveCodechefUser(username: String?) : Flow<Response<String>>
    suspend fun saveCodeforcesUser(username: String?) : Flow<Response<String>>
    suspend fun saveLeetcodeUser(username: String?) : Flow<Response<String>>


    fun getCodechefUser() : Flow<String?>
    fun getCodeforcesUser() : Flow<String?>
    fun getLeetcodeUser() : Flow<String?>


    // Remote Api data
    /**  Codechef Api Data **/
    suspend fun getCodechefData(username : String) : Flow<Response<Codechef>?>


    /**  Codeforces Api Data **/
    suspend fun getCodeforcesScreenData(username : String) : Flow<Response<CodeforcesScreenData>?> // RatingGraphData + UserInfo
    suspend fun getCodeforcesUserRatingChange(username : String) : Flow<Response<UserRatingChanges>?>
    suspend fun getCodeforcesProblemset(tags : String) : Flow<Response<CodeforcesProblemset>?>


    /**  Leetcode Api Data **/
    suspend fun getLeetCodeData(username: String) : Flow<Response<Leetcode>?>

    /** All Contest Api Data **/
    suspend fun getAllContestData() : Flow<Response<Contest>?>


}