package com.fabian.gestortask.ui.presentation.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabian.gestortask.domain.model.User
import com.fabian.gestortask.domain.usecases.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConfiguracionViewModel @Inject constructor(
    private val userUseCases: UserUseCase
) : ViewModel() {

    suspend fun getUserProfile(uid: String): User? {
        return withContext(Dispatchers.IO) {
            try {
                userUseCases.getUserProfile(uid)
            } catch (e: Exception) {
                null
            }
        }
    }
}
