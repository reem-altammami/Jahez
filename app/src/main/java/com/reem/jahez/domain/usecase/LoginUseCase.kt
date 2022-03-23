package com.reem.jahez.domain.usecase

import com.reem.jahez.domain.repository.AuthRepository
import com.reem.jahez.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class LoginUseCase @Inject constructor(val authRepository: AuthRepository) {
    suspend operator  fun invoke(email:String, password:String) : Flow<Response<Boolean>> = flow{
        try {
            emit(Response.Loading())
            val isLogin = authRepository.signIn(email, password)
            emit(Response.Success(isLogin))
        }catch (e : Exception){
            emit(Response.Error(e.message.toString()))
        }
    }
}