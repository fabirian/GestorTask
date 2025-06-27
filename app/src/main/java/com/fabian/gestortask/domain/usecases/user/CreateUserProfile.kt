package com.fabian.gestortask.domain.usecases.user

import com.fabian.gestortask.domain.model.User
import com.fabian.gestortask.domain.repository.UserRepository

class CreateUserProfile(private val repository: UserRepository) {
    suspend operator fun invoke(user: User) {
        repository.createUserProfile(user)
    }
}