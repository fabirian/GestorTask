package com.fabian.gestortask.domain.usecases.tasklist

import com.fabian.gestortask.domain.repository.TaskListRepository

class FetchDefaultListId (private val repository: TaskListRepository) {
    suspend operator fun invoke(userId: String): String = repository.fetchDefaultListId(userId)
}