package com.reem.jahez.domain.usecase

import com.reem.jahez.domain.repository.AuthRepository
import com.reem.jahez.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateNewUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userName:String, email:String, password:String) : Flow<Resource<Boolean>> = flow{
try {
    emit(Resource.Loading())
    val isRegister = authRepository.createNewUser(userName, email, password)
    emit(Resource.Success(isRegister))
} catch (e : Exception){
    emit(Resource.Error(e.message.toString()))
}
    }
}