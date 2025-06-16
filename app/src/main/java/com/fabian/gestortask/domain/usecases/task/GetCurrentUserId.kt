package com.fabian.gestortask.domain.usecases.task

import com.fabian.gestortask.domain.repository.TaskRepository

class GetCurrentUserId(private val repository: TaskRepository) {
    suspend operator fun invoke(): String? {
        return repository.getCurrentUserId()
    }
}
