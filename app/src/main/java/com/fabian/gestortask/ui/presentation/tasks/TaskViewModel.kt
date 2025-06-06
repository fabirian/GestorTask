package com.fabian.gestortask.ui.presentation.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.usecases.task.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {
    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set

    fun loadTasks() {
        viewModelScope.launch {
            tasks = taskUseCases.getTasks()
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskUseCases.addTask(task)
            loadTasks()
        }
    }

    fun deleteTask(task: Int) {
        viewModelScope.launch {
            taskUseCases.deleteTask(task)
            loadTasks()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskUseCases.updateTask(task)
            loadTasks()
        }
    }
}
