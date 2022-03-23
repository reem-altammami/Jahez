package com.reem.jahez.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthDataSource {
    suspend fun createNewUser(userName : String , email:String, password:String) : Flow<AuthResult> = flow{
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
    }

    suspend fun logIn(email:String,password:String) :Flow<AuthResult> = flow {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
    }
}