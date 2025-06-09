package com.fabian.gestortask.domain.usecases.task

import com.fabian.gestortask.domain.model.Task

data class TaskUseCases(
    val getTasks: GetTasks,
    val addTask: AddTask,
    val updateTask: UpdateTask,
    val deleteTask: DeleteTask,
    val getTaskById: GetTaskById
)
