package com.fabian.gestortask.domain.repository

import com.fabian.gestortask.domain.model.User

interface UserRepository {
    suspend fun createUserProfile(user: User)
    suspend fun getUserProfile(uid: String): User?
}