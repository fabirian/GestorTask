package com.fabian.gestortask.domain.usecases.user

import com.fabian.gestortask.domain.model.User
import com.fabian.gestortask.domain.repository.UserRepository

class GetUserProfile (private val userRepository: UserRepository) {
    suspend operator fun invoke(uid: String): User? {
        return userRepository.getUserProfile(uid)
    }
}
