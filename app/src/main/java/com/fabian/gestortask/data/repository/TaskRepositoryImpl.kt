package com.fabian.gestortask.data.repository

import com.fabian.gestortask.data.remote.TaskRepositoryRemote
import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val remote: TaskRepositoryRemote,
) : TaskRepository {

    override suspend fun getTasks(): List<Task> = remote.getTasks()

    override suspend fun addTask(task: Task){
        val maxPosition = remote.getTasksByListId(task.listId).maxByOrNull { it.position }?.position ?: 0

        val taskWithNewPosition = task.copy(position = maxPosition + 1)

        remote.addTask(taskWithNewPosition)
    }

    override suspend fun getTaskById(id: String): Task? = remote.getTaskById(id)

    override suspend fun updateTask(task: Task) = remote.updateTask(task)

    override suspend fun deleteTask(id: String) = remote.deleteTask(id)

    override suspend fun getTasksByListId(listId: String): List<Task> = remote.getTasksByListId(listId)

    override suspend fun getCurrentUserId(): String? = remote.getCurrentUserId()

    override suspend fun updateTasksPosition(tasks: List<Task>) = remote.updateTasksPosition(tasks)
}
