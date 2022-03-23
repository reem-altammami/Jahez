package com.reem.jahez.domain.usecase

import com.reem.jahez.domain.repository.AuthRepository
import javax.inject.Inject

class CreateNewUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userName:String, email:String, password:String){

        authRepository.createNewUser(userName, email, password)
    }
}