package com.reem.jahez.domain.repository

import com.google.firebase.auth.AuthResult
import com.reem.jahez.data.AuthDataSource
import com.reem.jahez.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun createNewUser(userName:String, email:String,password:String): Flow<Resource<Boolean>>
    suspend fun signIn( email:String,password:String): Flow<Resource<Boolean>>
}