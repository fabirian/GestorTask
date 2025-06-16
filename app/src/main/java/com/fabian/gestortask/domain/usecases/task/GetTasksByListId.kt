package com.fabian.gestortask.domain.usecases.task

import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.repository.TaskRepository

class GetTasksByListId (private val repository: TaskRepository) {
    suspend operator fun invoke(listId: String): List<Task> {
        return repository.getTasksByListId(listId)
    }
}