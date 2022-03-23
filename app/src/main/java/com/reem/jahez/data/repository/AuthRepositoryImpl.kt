package com.reem.jahez.data.repository

import com.google.firebase.auth.AuthResult
import com.reem.jahez.data.AuthDataSource
import com.reem.jahez.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) : AuthRepository{
    override suspend fun createNewUser( userName:String, email:String,password:String): Boolean{
        return authDataSource.createNewUser(userName, email, password)
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        return authDataSource.logIn(email, password)
    }
}