package com.reem.jahez.data

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthDataSource {

    suspend fun createNewUser(userName : String , email:String, password:String) : Boolean {
        var isRegister = false
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = userName
                    }
                    Firebase.auth.currentUser?.updateProfile(profileUpdates)
                    isRegister=true
                } else {
                    isRegister= false
                }

            }.await()
        return isRegister
    }
    suspend fun logIn(email:String,password:String) :Boolean {
        var isLogin = false
        Firebase.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            isLogin = task.isSuccessful
        }.await()
        return isLogin
    }
}