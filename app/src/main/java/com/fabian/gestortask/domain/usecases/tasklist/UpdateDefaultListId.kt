package com.fabian.gestortask.domain.usecases.tasklist

import com.fabian.gestortask.domain.repository.TaskListRepository

class UpdateDefaultListId (private val repository: TaskListRepository) {
    suspend operator fun invoke(userId: String, listId: String) = repository.updateDefaultListId(userId, listId)
}