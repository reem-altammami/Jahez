package com.reem.jahez.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.reem.jahez.domain.usecase.LoginUseCase
import com.reem.jahez.domain.models.AuthUiState
import com.reem.jahez.domain.models.Response
import com.reem.jahez.domain.models.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase) : ViewModel() {

    private var _loginUiState = MutableSharedFlow<AuthUiState>()
    val loginUiState: SharedFlow<AuthUiState> = _loginUiState

    private var _validation= MutableStateFlow(Validation())
    val validation: SharedFlow<Validation> = _validation

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).collect {
                when (it) {
                    is Response.Loading -> _loginUiState.emit(AuthUiState(isLoading = true))
                    is Response.Success -> _loginUiState.emit(AuthUiState(data = it.data))
                    is Response.Error -> _loginUiState.emit(AuthUiState(message = it.message.toString()))
                }
            }
        }
    }


fun validation(email:String ,password: String){
    validationEmail(email)
    validationPassword(password)
}
    private fun validationEmail(email : String){
        when {
            email.isEmpty() -> {
                _validation.update {
                    it.copy(email = 0)
                }
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _validation.update {
                    it.copy(email = 2)
                }
            }
            else -> {
                _validation.update {
                    it.copy(email = 1)
                }
            }
        }
    }
    private fun validationPassword(password: String){
when{
    password.isEmpty() -> {
        _validation.update {
            it.copy(password = 0)
        }
    }
    password.length < 6 ->{
        _validation.update {
            it.copy(password = 2)
        }
    }
    else -> {
        _validation.update {
            it.copy(password = 1)
        }
    }
}
    }

}

