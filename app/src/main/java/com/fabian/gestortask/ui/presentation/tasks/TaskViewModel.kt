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
    remoteRepository: TaskRepositoryRemote
) : ViewModel() {

    var searchQuery by mutableStateOf("")
    var selectedTag by mutableStateOf("")
    var selectedListId by mutableStateOf<String?>(null)
    var showOnlyCompleted by mutableStateOf<Boolean?>(null)

    var isTaskSaved by mutableStateOf(false)
        private set

    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    var currentTask by mutableStateOf<Task?>(null)
        private set

    private var currentListId by mutableStateOf<String?>(null)

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

    private fun loadTasksByListId(listId: String) {
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

    private fun addTask(task: Task) {
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

    fun addNewTask(title: String, description: String, tag: String, tagColor: String) {
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
                userId = userId,
                tag = tag,
                tagColor = tagColor
            )

            addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                useCaseTask.updateTask(task)
                currentListId?.let { loadTasksByListId(it) } ?: loadTasks()
                isTaskSaved = true
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al actualizar tarea", e)
            }
        }
    }

    fun markTaskAsCompletedInstant(task: Task) {
        val updatedTask = task.copy(isDone = true)

        val index = _tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            _tasks[index] = updatedTask
        }

        viewModelScope.launch {
            try {
                useCaseTask.updateTask(updatedTask)
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al completar tarea", e)
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

    fun reorderTasks(newTasks: List<Task>) {
        viewModelScope.launch {
            try {
                useCaseTask.updateTaskPosition(newTasks)
                _tasks.clear()
                _tasks.addAll(newTasks.sortedBy { it.position })
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al actualizar posiciones de tareas", e)
            }
        }
    }
    val filteredTasks: List<Task>
        get() = tasks.filter { task ->
            (searchQuery.isBlank() || task.title.contains(searchQuery, ignoreCase = true) || task.description.contains(searchQuery, ignoreCase = true)) &&
                    (selectedTag.isBlank() || task.tag == selectedTag) &&
                    (selectedListId == null || task.listId == selectedListId) &&
                    (showOnlyCompleted == null || task.isDone == showOnlyCompleted)
        }.sortedBy { it.position }

}
