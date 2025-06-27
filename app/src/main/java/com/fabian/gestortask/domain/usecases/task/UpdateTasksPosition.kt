package com.fabian.gestortask.domain.usecases.task

import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.repository.TaskRepository

class UpdateTasksPosition(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(tasks: List<Task>) {
        repository.updateTasksPosition(tasks)
    }
}

