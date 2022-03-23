package com.reem.jahez.domain.repository

import com.google.firebase.auth.AuthResult
import com.reem.jahez.data.AuthDataSource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun createNewUser(userName:String, email:String,password:String): Flow<AuthResult>
    suspend fun signIn( email:String,password:String): Flow<AuthResult>
}