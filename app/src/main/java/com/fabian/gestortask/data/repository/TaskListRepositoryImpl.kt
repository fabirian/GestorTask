package com.fabian.gestortask.data.repository

import com.fabian.gestortask.data.remote.TaskListRepositoryRemote
import com.fabian.gestortask.domain.model.TaskList
import com.fabian.gestortask.domain.repository.TaskListRepository
import javax.inject.Inject

class TaskListRepositoryImpl @Inject constructor(
    private val remoteDataSource: TaskListRepositoryRemote
) : TaskListRepository {

    override suspend fun deleteList(listId: String) {
        remoteDataSource.deleteList(listId)
    }

    override suspend fun getLists(): List<TaskList> {
        return remoteDataSource.getLists()
    }

    override suspend fun getListById(listId: String): TaskList? {
        return remoteDataSource.getListById(listId)
    }

    override suspend fun addList(taskList: TaskList) {
        remoteDataSource.addList(taskList)
    }

    override suspend fun updateList(taskList: TaskList) {
        remoteDataSource.updateList(taskList)
    }

    override suspend fun fetchDefaultListId(userId: String): String {
        return remoteDataSource.fetchDefaultListId(userId)
    }

    override suspend fun updateDefaultListId(userId: String, listId: String) {
        remoteDataSource.updateDefaultListId(userId, listId)
    }
}