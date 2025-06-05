package com.fabian.gestortask.domain.usecases.task

data class TaskUseCases(
    val addTask: AddTask,
    val getTasks: GetTasks,
    val deleteTask: DeleteTask,
    val updateTask: UpdateTask
)