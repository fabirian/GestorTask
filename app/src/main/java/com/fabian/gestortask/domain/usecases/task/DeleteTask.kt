package com.fabian.gestortask.domain.usecases.task

import com.fabian.gestortask.domain.repository.TaskRepository

class DeleteTask(private val repository: TaskRepository){
    suspend operator fun invoke(task: Int) = repository.deleteTask(task)
}