package com.fabian.gestortask.ui.presentation.tasks

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabian.gestortask.data.remote.TaskRepositoryRemote
import com.fabian.gestortask.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val remoteRepository: TaskRepositoryRemote
) : ViewModel() {

    var isTaskSaved by mutableStateOf(false)
        private set

    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    var currentTask by mutableStateOf<Task?>(null)
        private set

    fun loadTasks() {
        viewModelScope.launch {
            val taskList = remoteRepository.getTasks()
            _tasks.clear()
            _tasks.addAll(taskList)
        }
    }

    fun loadTaskById(id: String) {
        viewModelScope.launch {
            currentTask = remoteRepository.getTaskById(id)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            remoteRepository.addTask(task)
            loadTasks()
            isTaskSaved = true
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            remoteRepository.updateTask(task)
            loadTasks()
            isTaskSaved = true
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            remoteRepository.deleteTask(id)
            loadTasks()
        }
    }

    fun resetSaveState() {
        isTaskSaved = false
    }
}
