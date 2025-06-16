package com.fabian.gestortask.domain.usecases.tasklist

import com.fabian.gestortask.domain.model.TaskList
import com.fabian.gestortask.domain.repository.TaskListRepository

class GetListById (private val repository: TaskListRepository) {
    suspend operator fun invoke(listId: String): TaskList? = repository.getListById(listId)
}
