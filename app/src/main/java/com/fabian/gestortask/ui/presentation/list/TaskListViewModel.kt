package com.fabian.gestortask.ui.presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabian.gestortask.domain.model.TaskList
import com.fabian.gestortask.domain.usecases.tasklist.TaskListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val useCaseList: TaskListUseCases,
) : ViewModel() {

    var currentTask by mutableStateOf<TaskList?>(null)
        private set

    var isTaskSaved by mutableStateOf(false)
        private set

    private val _taskLists = mutableStateListOf<TaskList>()
    val taskLists : List<TaskList> get() = _taskLists

    fun loadLists() {
        viewModelScope.launch {
            try {
                val taskList = useCaseList.getLists()
                _taskLists.clear()
                _taskLists.addAll(taskList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addList(taskList: TaskList) {
        viewModelScope.launch {
            try {
                useCaseList.addList(taskList)
                isTaskSaved = true
                loadLists()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteList(id: String) {
        viewModelScope.launch {
            try {
                useCaseList.deleteList(id)
                loadLists()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateList(taskList: TaskList) {
        viewModelScope.launch {
            try {
                useCaseList.updateList(taskList)
                isTaskSaved = true
                loadLists()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getListById(listId: String) {
        viewModelScope.launch {
            try {
                val taskList = useCaseList.getListById(listId)
                currentTask = taskList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun resetSaveState() {
        isTaskSaved = false
    }
}
