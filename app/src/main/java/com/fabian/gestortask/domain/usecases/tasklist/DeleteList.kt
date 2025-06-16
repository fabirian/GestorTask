package com.fabian.gestortask.domain.usecases.tasklist

import com.fabian.gestortask.domain.repository.TaskListRepository

class DeleteList(private val repository: TaskListRepository) {
    suspend operator fun invoke(listId: String) = repository.deleteList(listId)
}
