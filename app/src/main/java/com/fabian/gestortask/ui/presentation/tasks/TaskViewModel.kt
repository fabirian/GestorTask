package com.fabian.gestortask.ui.presentation.tasks

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabian.gestortask.data.remote.TaskRepositoryRemote
import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.domain.model.TaskList
import com.fabian.gestortask.domain.usecases.task.TaskUseCases
import com.fabian.gestortask.domain.usecases.tasklist.TaskListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val useCaseTask: TaskUseCases,
    private val useCaseList: TaskListUseCases,
    private  val remoteRepository: TaskRepositoryRemote
) : ViewModel() {

    var isTaskSaved by mutableStateOf(false)
        private set

    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    var currentTask by mutableStateOf<Task?>(null)
        private set

    var currentListId by mutableStateOf<String?>(null)
        private set

    private val _userLists = mutableStateListOf<TaskList>()
    val userLists: List<TaskList> get() = _userLists

    var defaultListId by mutableStateOf<String?>(null)
        private set

    val userId = remoteRepository.getCurrentUserId()?: ""

    var isDefaultListLoaded by mutableStateOf(false)
        private set


    fun getUserLists() {
        viewModelScope.launch {
            try {
                val lists = useCaseList.getLists()
                _userLists.clear()
                _userLists.addAll(lists)
                Log.d("TaskViewModel", "Listas obtenidas: ${lists.size}")
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al obtener listas del usuario", e)
            }
        }
    }

    fun loadDefaultListId(userId: String) {
        viewModelScope.launch {
            try {
                val defaultId = useCaseList.fetchDefaultListId(userId)
                defaultListId = defaultId
                if (currentListId == null) currentListId = defaultId
                isDefaultListLoaded = true
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al obtener lista por defecto", e)
                isDefaultListLoaded = true
            }
        }
    }

    fun setDefaultListId(userId: String, listId: String) {
        viewModelScope.launch {
            try {
                useCaseList.updateDefaultListId(userId, listId)
                defaultListId = listId
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al actualizar lista por defecto", e)
            }
        }
    }

    fun loadTasks() {
        viewModelScope.launch {
            try {
                val taskList = useCaseTask.getTasks()
                _tasks.clear()
                _tasks.addAll(taskList)
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al cargar tareas", e)
            }
        }
    }

    fun loadTasksByListId(listId: String) {
        viewModelScope.launch {
            try {
                val taskList = useCaseTask.getTasksByListId(listId)
                _tasks.clear()
                _tasks.addAll(taskList)
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al cargar tareas por lista", e)
            }
        }
    }

    fun loadTaskById(id: String) {
        viewModelScope.launch {
            try {
                currentTask = useCaseTask.getTaskById(id)
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al cargar tarea por ID", e)
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                useCaseTask.addTask(task)
                currentListId?.let { loadTasksByListId(it) } ?: loadTasks()
                isTaskSaved = true
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al añadir tarea", e)
            }
        }
    }

    fun addNewTask(title: String, description: String) {
        viewModelScope.launch {
            val finalListId = currentListId ?: defaultListId ?: run {
                Log.e("TaskViewModel", "No hay lista disponible para asignar")
                return@launch
            }

            val task = Task(
                id = "",
                title = title,
                description = description,
                isDone = false,
                listId = finalListId,
                userId = userId
            )

            addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                useCaseTask.updateTask
                currentListId?.let { loadTasksByListId(it) } ?: loadTasks()
                isTaskSaved = true
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al actualizar tarea", e)
            }
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            try {
                useCaseTask.deleteTask(id)
                currentListId?.let { loadTasksByListId(it) } ?: loadTasks()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al eliminar tarea", e)
            }
        }
    }

    fun loadDefaultListTasks() {
        viewModelScope.launch {
            try {
                val listId = defaultListId ?: useCaseList.fetchDefaultListId(userId).also {
                    defaultListId = it
                    currentListId = it
                }

                val tasks = useCaseTask.getTasksByListId(listId)
                _tasks.clear()
                _tasks.addAll(tasks)
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al cargar tareas de la lista por defecto", e)
            }
        }
    }

    fun changeList(listId: String) {
        currentListId = listId
        loadTasksByListId(listId)
    }

    fun resetSaveState() {
        isTaskSaved = false
    }

}
