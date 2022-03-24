package com.reem.jahez.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reem.jahez.domain.usecase.CreateNewUserUseCase
import com.reem.jahez.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RegistrationViewModel @Inject constructor(private val createNewUserUseCase: CreateNewUserUseCase) :ViewModel() {

   private var _registrationUiState = MutableSharedFlow<RegistrationUiState>()
    val registrationUiState : SharedFlow<RegistrationUiState> = _registrationUiState


    fun createNewUser (userName:String,email: String, password:String){
        viewModelScope.launch {
          createNewUserUseCase(userName,email, password).onEach {
               when(it){
                   is Response.Loading -> _registrationUiState.emit(RegistrationUiState(isLoading = true))
                   is  Response.Success -> _registrationUiState.emit(RegistrationUiState(data = it.data))
                   is Response.Error -> _registrationUiState.emit(RegistrationUiState(message = it.message.toString()))
               }

           }.launchIn(viewModelScope)
        }

    }


}