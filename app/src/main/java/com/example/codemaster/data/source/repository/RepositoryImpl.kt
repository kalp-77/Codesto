package com.example.codemaster.data.source.repository

import android.util.Log
import com.example.codemaster.data.model.Codechef
import com.example.codemaster.data.model.Contest
import com.example.codemaster.data.model.Leetcode
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.model.codeforces_model.CodeforcesProblemset
import com.example.codemaster.data.model.codeforces_model.UserRatingChanges
import com.example.codemaster.data.source.remote.CFCCApi
import com.example.codemaster.data.source.remote.CodeforcesOfficialApi
import com.example.codemaster.data.source.remote.ContestApi
import com.example.codemaster.data.source.remote.LeetcodeApi
import com.example.codemaster.ui.screens.codeforces.CodeforcesScreenData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

// provide AuthRepositoryImpl inside app module
class RepositoryImpl @Inject constructor(
    private val codeforcesApi: CodeforcesOfficialApi,
    private val cfccApi: CFCCApi,
    private val leetcodeApi: LeetcodeApi,
    private val contestApi: ContestApi,
    private val firebaseAuth: FirebaseAuth,
    private val db : DatabaseReference
) : Repository {


    /** Firebase authentication repository implementation **/

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun loginUser(email: String, password: String): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Response.Success(result))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }

    override suspend fun signupUser(
        name: String,
        email: String,
        password: String
    ): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(userProfileChangeRequest {
                displayName = name
            })?.await()
            emit(Response.Success(result))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }


    /** Firebase database repository implementation **/

    override suspend fun saveCodechefUser(username: String?): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading())
            db.child(firebaseAuth.currentUser?.uid.toString()).child("codechef")
                .setValue(username).await()
            emit(Response.Success("codechef username saved"))
        } catch (e: Exception) {
            emit(Response.Failure(e.toString()))
        }
    }

    override suspend fun saveCodeforcesUser(username: String?): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading())
            db.child(firebaseAuth.currentUser?.uid.toString()).child("codeforces").setValue(username).await()
            emit(Response.Success("codeforces username saved"))
        } catch (e: Exception) {
            emit(Response.Failure(e.toString()))
        }
    }


    override suspend fun saveLeetcodeUser(username: String?): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading())
            db.child(firebaseAuth.currentUser?.uid.toString()).child("leetcode").setValue(username).await()
            emit(Response.Success("leetcode username saved"))
        } catch (e: Exception) {
            emit(Response.Failure(e.toString()))
        }
    }

    override fun getCodechefUser(): Flow<String?> {
        return db.child(firebaseAuth.currentUser?.uid.toString()).child("codechef").snapshots.map {
            it.value.toString()
        }
    }

    override fun getCodeforcesUser(): Flow<String?> {
        return db.child(firebaseAuth.currentUser?.uid.toString()).child("codeforces").snapshots.map {
            it.value.toString()
        }
    }

    override fun getLeetcodeUser(): Flow<String?> {
        return db.child(firebaseAuth.currentUser?.uid.toString()).child("leetcode").snapshots.map {
            it.value.toString()
        }
    }

    override suspend fun getCodechefData(username: String): Flow<Response<Codechef>?> {
        return flow<Response<Codechef>?> {
            emit(Response.Loading())
            val data = cfccApi.getCodechefData(username)?.body()
            emit(Response.Success(data = data))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }


    /** Codeforces Screen Implementation **/
    override suspend fun getCodeforcesScreenData(username: String): Flow<Response<CodeforcesScreenData>?> {   // graphData + userInfo
        return flow {
            emit(Response.Loading())
            val userData = codeforcesApi.getCodeforcesUserInfo("kalp-77")?.body()
            val graphData = codeforcesApi.getUserRatingChanges("kalp-77")?.body()
            emit(Response.Success(data = CodeforcesScreenData(userData!!, graphData!!)))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }

    override suspend fun getCodeforcesUserRatingChange(username: String): Flow<Response<UserRatingChanges>?> {
        return  flow{
            emit(Response.Loading())
            val data = codeforcesApi.getUserRatingChanges(username)?.body()
            emit(Response.Success(data))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }

    override suspend fun getCodeforcesProblemset(tags: String): Flow<Response<CodeforcesProblemset>?> {
        return flow{
            emit(Response.Loading())
            val data = codeforcesApi.getUserProblemset(tags)?.body()
            emit(Response.Success(data = data))
        } .catch {
            emit(Response.Failure(it.message.toString()))
        }
    }

    override suspend fun getLeetCodeData(username: String): Flow<Response<Leetcode>?> {
        return flow{
            emit(Response.Loading())
            val data = leetcodeApi.getLeetCodeData(username)?.body()
            emit(Response.Success(data))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }

    override suspend fun getAllContestData(): Flow<Response<Contest>?> {
        return flow{
            emit(Response.Loading())
            val data = contestApi.getContestDetailsData()?.body()
            emit(Response.Success(data = data))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }
}