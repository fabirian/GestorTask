package com.fabian.gestortask.domain.usecases.task

import com.fabian.gestortask.domain.repository.TaskRepository

class GetTasks(private val repository: TaskRepository) {
    suspend operator fun invoke() = repository.getTasks()
}