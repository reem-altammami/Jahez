package com.reem.jahez.base

import android.app.UiAutomation
import androidx.lifecycle.ViewModel
import com.reem.jahez.domain.models.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {
    val _uiState : MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState : StateFlow<UiState> = _uiState
}