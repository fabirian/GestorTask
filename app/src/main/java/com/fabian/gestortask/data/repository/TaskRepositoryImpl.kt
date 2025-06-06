package com.fabian.gestortask.data.repository

import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.repository.TaskRepository

class TaskRepositoryImpl : TaskRepository {
    override suspend fun getTasks(): List<Task> {
        return emptyList()
    }

    override suspend fun addTask(task: Task) { }
    override suspend fun getTaskById(id: Int): Task? = null
    override suspend fun updateTask(task: Task) {}
    override suspend fun deleteTask(task: Int) {}
}