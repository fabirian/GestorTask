package com.fabian.gestortask.domain.usecases.task

import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.repository.TaskRepository

class GetTaskById(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: String): Task? {
        return repository.getTaskById(id)
    }
}
