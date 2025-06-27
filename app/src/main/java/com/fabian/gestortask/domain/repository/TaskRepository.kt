package com.fabian.gestortask.domain.repository

import com.fabian.gestortask.domain.model.Task


interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun addTask(task: Task)
    suspend fun getTaskById(id: String): Task?
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: String)
    suspend fun getTasksByListId(listId: String): List<Task>
    suspend fun getCurrentUserId(): String?
    suspend fun updateTasksPosition(tasks: List<Task>)
}

