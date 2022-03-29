package com.reem.jahez.domain.usecase

import com.reem.jahez.domain.repository.AuthRepository
import com.reem.jahez.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateNewUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userName:String, email:String, password:String) : Flow<Resource<Boolean>>
 {
    return authRepository.createNewUser(userName, email, password)
    }
}