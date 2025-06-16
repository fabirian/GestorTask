package com.fabian.gestortask.domain.usecases.tasklist

data class TaskListUseCases(
    val getLists: GetLists,
    val addList: AddList,
    val deleteList: DeleteList,
    val updateList: UpdateList,
    val getListById: GetListById,
    val fetchDefaultListId: FetchDefaultListId,
    val updateDefaultListId: UpdateDefaultListId,
)
