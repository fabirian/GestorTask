package com.fabian.gestortask.domain.usecases.task

import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.repository.TaskRepository

class AddTask(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.addTask(task)
}