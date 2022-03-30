package com.reem.jahez.base

import android.app.UiAutomation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reem.jahez.domain.models.Resource
import com.reem.jahez.domain.models.UiState
import com.reem.jahez.domain.models.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {
    val _uiState : MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState : StateFlow<UiState> = _uiState

    private var _validation : MutableStateFlow<Validation> = MutableStateFlow(Validation())
    val validation :StateFlow <Validation> = _validation

    fun validation(name:String,email:String,password: String):Boolean{
        validationUsername(name)
        validationEmail(email)
        validationPassword(password)
        return  validationUsername(name)&&
                validationEmail(email)&&
                validationPassword(password)
    }
    private fun validationUsername(userName: String): Boolean {
        return when {
            userName.isEmpty() -> {
                _validation.update {
                    it.copy(userName = 0)
                }
                false
            }
            else->{
                _validation.update {
                    it.copy(userName = 1)
                }
                true
            }
        }
    }

    private fun validationEmail(email: String): Boolean {
        when {
            email.isEmpty() -> {
                _validation.update {
                    it.copy(email = 0)
                }
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _validation.update {
                    it.copy(email = 2)
                }
                return false
            }
            else -> {
                _validation.update {
                    it.copy(email = 1)
                }
                return true
            }
        }
    }

    private fun validationPassword(password: String): Boolean {
        when {
            password.isEmpty() -> {
                _validation.update {
                    it.copy(password = 0)
                }
                return false
            }
            password.length < 6 -> {
                _validation.update {
                    it.copy(password = 2)
                }
                return false
            }
            else -> {
                _validation.update {
                    it.copy(password = 1)

                }
                return true
            }

        }
    }

    fun <T> collectFlow(flow: Flow<T>, data: (T) -> Unit) {
        viewModelScope.launch {
            flow.collect{
                when (it) {
                    is Resource.Loading<*> -> _uiState.emit(UiState(isLoading = true))
                    is Resource.Success<*> -> {
                        data(it.data as T)
                        _uiState.emit(UiState())
                    }
                    is Resource.Error<*> -> _uiState.emit(UiState(message = it.message ?: ""))
                }
            }
        }
    }
}