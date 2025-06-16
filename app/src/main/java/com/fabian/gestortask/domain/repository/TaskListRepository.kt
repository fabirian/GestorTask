package com.fabian.gestortask.domain.repository

import com.fabian.gestortask.domain.model.TaskList

interface TaskListRepository {
    suspend fun getLists(): List<TaskList>
    suspend fun getListById(listId: String): TaskList?
    suspend fun addList(taskList: TaskList)
    suspend fun deleteList(listId: String)
    suspend fun updateList(taskList: TaskList)
    suspend fun fetchDefaultListId(userId: String): String
    suspend fun updateDefaultListId(userId: String, listId: String)
}
