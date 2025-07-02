package com.fabian.gestortask.ui.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabian.gestortask.auth.FirebaseAuthManager
import com.fabian.gestortask.domain.model.User
import com.fabian.gestortask.domain.usecases.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager,
    private val userCase: UserUseCase
) : ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        repeatPassword: String,
        onSuccess: () -> Unit
    ) {
        when {
            firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank() || repeatPassword.isBlank() -> {
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
                        val userId = authManager.getCurrentUserId() ?: ""
                        val user = User(
                            id = userId,
                            firstName = firstName,
                            lastName = lastName,
                            email = email
                        )

                        try {
                            userCase.createUserProfile(user)
                            _successMessage.value = "Registro exitoso. ¡Bienvenido, $firstName!"
                            _errorMessage.value = null
                            onSuccess()
                        } catch (e: Exception) {
                            _errorMessage.value = "Error al guardar perfil del usuario: ${e.message}"
                            _successMessage.value = null
                        }
                    } else {
                        _errorMessage.value = result.exceptionOrNull()?.localizedMessage ?: "Error al registrar usuario."
                        _successMessage.value = null
                    }
                }
            }
        }
    }
}

