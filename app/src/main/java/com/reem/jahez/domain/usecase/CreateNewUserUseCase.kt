package com.reem.jahez.domain.usecase

import com.reem.jahez.domain.repository.AuthRepository
import com.reem.jahez.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateNewUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userName:String, email:String, password:String) : Flow<Response<Boolean>> = flow{
try {
    emit(Response.Loading())
    val isRegister = authRepository.createNewUser(userName, email, password)
    emit(Response.Success(isRegister))
} catch (e : Exception){
    emit(Response.Error(e.message.toString()))
}
    }
}