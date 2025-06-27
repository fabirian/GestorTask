package com.fabian.gestortask.data.repository

import com.fabian.gestortask.data.remote.UserRemoteDataSource
import com.fabian.gestortask.domain.model.User
import com.fabian.gestortask.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun createUserProfile(user: User) {
        userRemoteDataSource.saveUser(user)
    }

    override suspend fun getUserProfile(uid: String): User? {
        return userRemoteDataSource.getUserProfile(uid)
    }
}
