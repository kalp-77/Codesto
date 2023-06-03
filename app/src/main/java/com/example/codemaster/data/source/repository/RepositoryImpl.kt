package com.example.codemaster.data.source.repository

import android.util.Log
import com.example.codemaster.R
import com.example.codemaster.data.model.Codechef
import com.example.codemaster.data.model.Contest
import com.example.codemaster.data.model.Leetcode
import com.example.codemaster.data.model.Response
import com.example.codemaster.data.model.codeforces_model.problem_solved.SolvedProblems
import com.example.codemaster.data.model.codeforces_model.problemset.CodeforcesProblemset
import com.example.codemaster.data.model.codeforces_model.rating_change.UserRatingChanges
import com.example.codemaster.data.model.codeforces_model.user_info.UserInfoResult
import com.example.codemaster.data.source.remote.CFCCApi
import com.example.codemaster.data.source.remote.CodeforcesOfficialApi
import com.example.codemaster.data.source.remote.ContestApi
import com.example.codemaster.data.source.remote.LeetcodeApi
import com.example.codemaster.ui.screens.codeforces.CodeforcesScreenData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
    private val db : DatabaseReference,
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
    ): Flow<Response<AuthResult>> = flow {
        emit(Response.Loading())
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(userProfileChangeRequest {
                displayName = name
            })?.await()
//            val uid = firebaseAuth.currentUser?.uid.toString()
//            db.child(uid).child("username").setValue(name).await()
        } catch (e: Exception) {
            emit(Response.Failure(e.message.toString()))
        }
    }

    override suspend fun updateUsername(username: String): Flow<Response<String>> = flow {
        val user = FirebaseAuth.getInstance().currentUser

// Create a UserProfileChangeRequest object with the updated display name (username)
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()
        emit(Response.Loading())
        try {
            if (user?.updateProfile(profileUpdates)?.isSuccessful == true) {
                emit(Response.Success(data = "Updated successfully"))
            } else {
                emit(Response.Success(data = "Failed to update username"))
            }
        } catch(e:Exception) {
            emit(Response.Failure(message = e.message.toString()))
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun savePlatformUser(
        username: String,
        ccUsername: String,
        cfUsername: String,
        lcUsername: String
    ): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading())
            val user = FirebaseAuth.getInstance().currentUser
            UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()
//            if (user?.updateProfile(profileUpdates)?.isSuccessful == false) {
//                emit(Response.Failure(message = "Failed to update username"))
//            }
            val uid = firebaseAuth.currentUser?.uid.toString()
            db.child(uid).child("codechef").setValue(ccUsername).await()
            db.child(uid).child("codeforces").setValue(cfUsername).await()
            db.child(uid).child("leetcode").setValue(lcUsername).await()
            emit(Response.Success("usernames updated successfully"))
        } catch (e: Exception) {
            emit(Response.Failure(e.toString()))
        }
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

    override suspend fun saveFriends(friend: UserInfoResult): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading(data = "Loading"))
            val uid = firebaseAuth.currentUser?.uid.toString()
            val friendsRef = db.child(uid).child("friends")
            val existingFriendSnapshot = friendsRef.orderByChild("handle").equalTo(friend.handle).get().await()



            if (!existingFriendSnapshot.exists()) {
                friendsRef.push().setValue(friend).await()
                emit(Response.Success("Friend successfully added"))
            } else {
                // Friend data already exists, handle accordingly
                emit(Response.Failure(" failure :Friend data already exists"))
            }
        } catch (e:Exception) {
            emit(Response.Failure(e.message.toString()))
        }
    }

    override suspend fun getAllFriends(): Flow<Response<List<UserInfoResult>>?> = flow {
        val uid = firebaseAuth.currentUser?.uid.toString()
        val friendsRef = db.child(uid).child("friends")
        friendsRef.get().await().run {
            try {
                val friendList = children.mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(UserInfoResult::class.java)
                }
                emit(Response.Success(friendList))
            } catch (e:Exception) {
                emit(Response.Failure(e.message.toString()))
            }
        }
    }


    override suspend fun deleteFriend(friend: UserInfoResult) {
        try {
            val userUid = firebaseAuth.currentUser?.uid.toString()
            val friendRef = db.child(userUid).child("friends")

            friendRef.orderByChild("handle").equalTo(friend.handle.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (issue in dataSnapshot.children) {
                            if (issue.getValue(UserInfoResult::class.java)?.handle == friend.handle) {
                                issue.ref.removeValue()
                                break  // Break the loop after deleting the first matching friend node
                            }
                        }
                    }

                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("kalp", "firebase: ${databaseError.details}}")

                }
            })
        } catch (e:Exception) {
            Log.d("kalp", "firebase: ${e.message.toString()}}")
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

    override suspend fun getUsername(): Flow<String?> = flow {
        val currentUser = FirebaseAuth.getInstance().currentUser
        emit(currentUser?.displayName)
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
            val userData = codeforcesApi.getCodeforcesUserInfo(username)?.body()
            val graphData = cfccApi.getCodeforcesData(username)?.body()
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
            val data = codeforcesApi.getUserProblemset(tags)?.body()!!
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

    override suspend fun getAllContestData(): Flow<Response<Contest>?>{
        return flow{
            emit(Response.Loading())
            val data = contestApi.getContestDetailsData()?.body()
            data?.forEach {
                when (it.site) {
                    "CodeChef" -> it.icon = R.drawable.icons_codechef
                    "CodeForces" -> it.icon = R.drawable.icons_codeforces
                    "LeetCode" -> it.icon = R.drawable.icons_leetcode
                    "HackerRank" -> it.icon = R.drawable.icons_hackerrank
                    "HackerEarth" -> it.icon = R.drawable.icons_hackerearth
                    "AtCoder" -> it.icon = R.drawable.icons_atcoder
                    else -> it.icon = R.drawable.icons_google
                }
            }
            emit(Response.Success(data = data))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }


    /** Codeforces indexed wise problem solved **/
    override suspend fun getSolvedProblemData(username: String): Flow<Response<SolvedProblems>?> {
        return flow {
            emit(Response.Loading())
            val data = codeforcesApi.getUserProblemSolvedStatus(username).body()
            emit(Response.Success(data = data))
        }.catch {
            emit(Response.Failure(it.message.toString()))
        }
    }


//    override suspend fun deleteCodeforcesFriend(username: String): Flow<Response<String>> = flow {
//        val usernameRef = db.child(firebaseAuth.currentUser?.uid.toString()).child("cf_friends")
//        val usernamesSnapshot = usernameRef.get().await()
//        try {
//            val usernamesArray = usernamesSnapshot.value as ArrayList<*> // Get the current array of usernames
//            val indexToRemove = usernamesArray.indexOf(username) // Get the index of the username to remove
//
//            if (indexToRemove > -1) { // If the username is in the array
//                usernamesArray.removeAt(indexToRemove) // Remove it from the array
//                usernameRef.setValue(usernamesArray).await() // Update the array in the database
//                emit(Response.Success("Removed friend"))
//            } else {
//                emit(Response.Success("No friends exist"))
//            }
//        }
//        catch (e: Exception) {
//            emit(Response.Failure(e.toString()))
//        }
//    }
//

}