package com.reem.jahez.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reem.jahez.domain.usecase.CreateNewUserUseCase
import com.reem.jahez.domain.models.AuthUiState
import com.reem.jahez.domain.models.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RegistrationViewModel @Inject constructor(private val createNewUserUseCase: CreateNewUserUseCase) :ViewModel() {

   private var _registrationUiState = MutableSharedFlow<AuthUiState>()
    val registrationUiState : SharedFlow<AuthUiState> = _registrationUiState


    fun createNewUser (userName:String,email: String, password:String){
        viewModelScope.launch {
          createNewUserUseCase(userName,email, password).onEach {
               when(it){
                   is Response.Loading -> _registrationUiState.emit(AuthUiState(isLoading = true))
                   is  Response.Success -> _registrationUiState.emit(AuthUiState(data = it.data))
                   is Response.Error -> _registrationUiState.emit(AuthUiState(message = it.message.toString()))
               }

           }.launchIn(viewModelScope)
        }

    }


}