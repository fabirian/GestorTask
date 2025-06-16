package com.fabian.gestortask.domain.usecases.task

data class TaskUseCases(
    val getTasks: GetTasks,
    val addTask: AddTask,
    val updateTask: UpdateTask,
    val deleteTask: DeleteTask,
    val getTaskById: GetTaskById,
    val getTasksByListId: GetTasksByListId,
    val getCurrentUserId: GetCurrentUserId
)
