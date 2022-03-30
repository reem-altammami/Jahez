package com.reem.jahez.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reem.jahez.base.BaseViewModel
import com.reem.jahez.domain.usecase.LoginUseCase
import com.reem.jahez.domain.models.Resource
import com.reem.jahez.domain.models.UiState
import com.reem.jahez.domain.models.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase) : BaseViewModel() {

    private var _loginUiState = MutableSharedFlow<Boolean>()
    val loginUiState: SharedFlow<Boolean> = _loginUiState

//    private var _validation= MutableStateFlow(Validation())
//    val validation: SharedFlow<Validation> = _validation

    fun login(email: String, password: String) {
        viewModelScope.launch {
            collectFlow(loginUseCase(email, password)){
                viewModelScope.launch {
                    _loginUiState.emit(it.data ?: false)
                }
            }
        }
    }


}

