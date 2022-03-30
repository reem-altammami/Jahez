package com.reem.jahez.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reem.jahez.base.BaseViewModel
import com.reem.jahez.domain.usecase.CreateNewUserUseCase

import com.reem.jahez.domain.models.Resource
import com.reem.jahez.domain.models.UiState
import com.reem.jahez.domain.models.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RegistrationViewModel @Inject constructor(private val createNewUserUseCase: CreateNewUserUseCase) :BaseViewModel() {

   private var _registrationUiState = MutableSharedFlow<Boolean>()
    val registrationUiState : SharedFlow<Boolean> = _registrationUiState
//    private var _validation : MutableStateFlow<Validation> = MutableStateFlow(Validation())
//    val validation :StateFlow <Validation> = _validation


    fun createNewUser (userName:String,email: String, password:String){
        viewModelScope.launch {
            collectFlow(createNewUserUseCase(userName, email, password)){
                viewModelScope.launch {
                    _registrationUiState.emit(it.data ?: false)
                }
            }
        }

    }


//    fun validation(name:String,email:String,password: String):Boolean{
//        validationUsername(name)
//        validationEmail(email)
//        validationPassword(password)
//        return  validationUsername(name)&&
//        validationEmail(email)&&
//        validationPassword(password)
//    }
//    private fun validationUsername(userName: String): Boolean {
//        return when {
//            userName.isEmpty() -> {
//                _validation.update {
//                    it.copy(userName = 0)
//                }
//                false
//            }
//            else->{
//                _validation.update {
//                    it.copy(userName = 1)
//                }
//                true
//            }
//        }
//    }
//
//    private fun validationEmail(email: String): Boolean {
//        when {
//            email.isEmpty() -> {
//                _validation.update {
//                    it.copy(email = 0)
//                }
//                return false
//            }
//            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
//                _validation.update {
//                    it.copy(email = 2)
//                }
//                return false
//            }
//            else -> {
//                _validation.update {
//                    it.copy(email = 1)
//                }
//                return true
//            }
//        }
//    }
//
//    private fun validationPassword(password: String): Boolean {
//        when {
//            password.isEmpty() -> {
//                _validation.update {
//                    it.copy(password = 0)
//                }
//                return false
//            }
//            password.length < 6 -> {
//                _validation.update {
//                    it.copy(password = 2)
//                }
//                return false
//            }
//            else -> {
//                _validation.update {
//                    it.copy(password = 1)
//
//                }
//                return true
//            }
//        }
//    }


}