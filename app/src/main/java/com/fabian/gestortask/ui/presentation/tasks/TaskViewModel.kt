package com.fabian.gestortask.ui.presentation.tasks

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.usecases.task.TaskUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    var isTaskSaved by mutableStateOf(false)
        private set

    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    var currentTask by mutableStateOf<Task?>(null)
        private set

    private val firestore = FirebaseFirestore.getInstance()

    fun loadTasks() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        firestore.collection("tasks")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val taskList = result.map { document ->
                    document.toObject(Task::class.java)
                }
                _tasks.clear()
                _tasks.addAll(taskList)
            }
            .addOnFailureListener { exception ->
                Log.e("TaskViewModel", "Error al cargar tareas", exception)
            }
    }

    fun loadTaskById(id: String) {
        viewModelScope.launch {
            val task = taskUseCases.getTaskById(id)
            currentTask = task
        }
    }

    fun addTask(task: Task) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val taskWithUser = task.copy(userId = userId)

        viewModelScope.launch {
            taskUseCases.addTask(taskWithUser)
            loadTasks()
            isTaskSaved = true
        }
    }

    fun updateTask(task: Task) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Asegurar que userId esté actualizado
        val taskWithUser = task.copy(userId = userId)

        viewModelScope.launch {
            taskUseCases.updateTask(taskWithUser)
            loadTasks()
            isTaskSaved = true
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            taskUseCases.deleteTask(id)
            loadTasks()
        }
    }

    fun resetSaveState() {
        isTaskSaved = false
    }
}
