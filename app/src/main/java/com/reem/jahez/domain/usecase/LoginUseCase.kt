package com.reem.jahez.domain.usecase

import com.reem.jahez.domain.repository.AuthRepository
import com.reem.jahez.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator  fun invoke(email:String, password:String) : Flow<Resource<Boolean>> = flow{
        try {
            emit(Resource.Loading())
            val isLogin = authRepository.signIn(email, password)
            emit(Resource.Success(isLogin))
        }catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}