package com.fabian.gestortask.domain.usecases.tasklist

import com.fabian.gestortask.domain.model.TaskList
import com.fabian.gestortask.domain.repository.TaskListRepository

class AddList(private val repository: TaskListRepository) {
    suspend operator fun invoke(taskList: TaskList) = repository.addList(taskList)
}
