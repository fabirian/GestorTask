package com.fabian.gestortask.ui.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabian.gestortask.auth.FirebaseAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager
) : ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    fun registerUser(name: String, email: String, password: String, repeatPassword: String, onSuccess: () -> Unit) {
        when {
            name.isBlank() || email.isBlank() || password.isBlank() || repeatPassword.isBlank() -> {
                _errorMessage.value = "Por favor completa todos los campos."
                _successMessage.value = null
            }
            password != repeatPassword -> {
                _errorMessage.value = "Las contraseñas no coinciden."
                _successMessage.value = null
            }
            else -> {
                viewModelScope.launch {
                    val result = authManager.createUser(email, password)
                    if (result.isSuccess) {
                        _successMessage.value = "Registro exitoso. ¡Bienvenido, $name!"
                        _errorMessage.value = null
                        onSuccess()
                    } else {
                        _errorMessage.value = result.exceptionOrNull()?.localizedMessage ?: "Error al registrar usuario."
                        _successMessage.value = null
                    }
                }
            }
        }
    }
}
