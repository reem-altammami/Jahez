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

}