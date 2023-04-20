package com.example.codemaster.data.source.repository

import com.example.codemaster.data.model.Response
import com.example.codemaster.data.model.codeforces_model.UserRatingChanges
import com.example.codemaster.ui.screens.codeforces.CodeforcesScreenData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

// provide AuthRepositoryImpl inside app module
class RepositoryImpl @Inject constructor(
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

    override suspend fun saveCodechefUser(username: String?): Flow<Response<String>> =
        callbackFlow {
            trySend(Response.Loading())
            db.child(firebaseAuth.currentUser?.uid.toString()).child("codechef").setValue(
                username
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Response.Success("codechef username saved"))
                }
            }.addOnFailureListener {
                trySend(Response.Failure(it.toString()))
            }
            awaitClose {
                close()
            }
        }

    override suspend fun saveCodeforcesUser(username: String?): Flow<Response<String>> =
        callbackFlow {
            trySend(Response.Loading())
            db.child(firebaseAuth.currentUser?.uid.toString()).child("codeforces").setValue(
                username
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Response.Success("codeforces username saved"))
                }
            }.addOnFailureListener {
                trySend(Response.Failure(it.toString()))
            }
            awaitClose {
                close()
            }
        }

    override suspend fun saveLeetcodeUser(username: String?): Flow<Response<String>> =
        callbackFlow {
            trySend(Response.Loading())
            db.child(firebaseAuth.currentUser?.uid.toString()).child("leetcode").setValue(
                username
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Response.Success("leetcode username saved"))
                }
            }.addOnFailureListener {
                trySend(Response.Failure(it.toString()))
            }
            awaitClose {
                close()
            }
        }

    override fun getCodechefUser(): Flow<String?> = callbackFlow {
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val codechefUser = snapshot.child(firebaseAuth.currentUser?.uid.toString())
                    .child("codechef").value.toString()
                trySend(codechefUser)
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(error.message)
            }
        }
        db.addValueEventListener(valueEvent)

        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }
    }

    override fun getCodeforcesUser(): Flow<String?> = callbackFlow {
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val codeforcesUser = snapshot.child(firebaseAuth.currentUser?.uid.toString())
                    .child("codeforces").value.toString()
                trySend(codeforcesUser)
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(error.message)
            }
        }
        db.addValueEventListener(valueEvent)

        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }
    }

    override fun getLeetcodeUser(): Flow<String?> = callbackFlow {
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val leetcodeUser = snapshot.child(firebaseAuth.currentUser?.uid.toString())
                    .child("leetcode").value.toString()
                trySend(leetcodeUser)
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(error.message)
            }
        }
        db.addValueEventListener(valueEvent)

        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }
    }


    /** Codeforces Screen Implementation **/
    override suspend fun getCodeforcesScreenData(username: String): Flow<Response<CodeforcesScreenData>?> {   // graphData + userInfo
        return flow {

        }
    }
}