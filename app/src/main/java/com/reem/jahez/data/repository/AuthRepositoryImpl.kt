package com.reem.jahez.data.repository

import com.google.firebase.auth.AuthResult
import com.reem.jahez.data.AuthDataSource
import com.reem.jahez.domain.models.Resource
import com.reem.jahez.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) :
    AuthRepository {
    override suspend fun createNewUser(
        userName: String,
        email: String,
        password: String
    ): Flow<Resource<Boolean>> = flow {

        emit(Resource.Loading())
        val response = authDataSource.createNewUser(userName, email, password)
        if (response.isSuccess) {
            emit(Resource.Success(data = response.isSuccess))
        } else {
            emit(Resource.Error(message = response.errorMessage))
        }

    }

    override suspend fun signIn(email: String, password: String): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading())
        val response = authDataSource.logIn(email, password)
        if (response.isSuccess) {
            emit(Resource.Success(data = response.isSuccess))
        } else {
            emit(Resource.Error(message = response.errorMessage))
        }    }
}

