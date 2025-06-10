package com.fabian.gestortask.ui.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabian.gestortask.auth.FirebaseAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager
) : ViewModel() {

    var loginResult by mutableStateOf<Result<Unit>?>(null)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginResult = authManager.login(email, password)
        }
    }

    fun logout() {
        authManager.logout()
    }

    fun resetLoginResult() {
        loginResult = null
    }
}
