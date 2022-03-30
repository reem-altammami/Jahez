package com.reem.jahez.data

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.reem.jahez.domain.models.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthDataSource {

    suspend fun createNewUser(userName : String , email:String, password:String) : AuthResponse {
        var authResponse = AuthResponse()
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = userName
                    }
                    Firebase.auth.currentUser?.updateProfile(profileUpdates)
                    authResponse= AuthResponse(isSuccess = task.isSuccessful)
                } else {
                    authResponse= AuthResponse(errorMessage = task.exception?.message.toString())
                }

            }.await()
        return authResponse
    }
    suspend fun logIn(email:String,password:String) :AuthResponse {
        var authResponse = AuthResponse()
        Firebase.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                authResponse = AuthResponse(isSuccess = task.isSuccessful)
            } else{
                authResponse = AuthResponse(errorMessage = task.exception?.message.toString())
            }
        }.await()
        return authResponse
    }
}