package com.fabian.gestortask.ui.presentation.tasks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.ui.presentation.tasks.components.TaskForm

@Composable
fun TaskScreen(
    navController: NavController,
    taskId: String?,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val isTaskSaved = viewModel.isTaskSaved
    val task = viewModel.currentTask

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(taskId) {
        taskId?.let { viewModel.loadTaskById(it) }
    }

    LaunchedEffect(isTaskSaved) {
        if (isTaskSaved) {
            navController.popBackStack()
            viewModel.resetSaveState()
        }
    }

    LaunchedEffect(task) {
        task?.let {
            title = it.title
            description = it.description
        }
    }

    TaskForm(
        title = title,
        onTitleChange = { title = it },
        description = description,
        onDescriptionChange = { description = it },
        onSaveClick = {
            val taskToSave = Task(
                id = taskId ?: "",
                title = title,
                description = description,
                isDone = task?.isDone ?: false,
                userId = task?.userId ?: ""
            )

            if (taskId == null) viewModel.addTask(taskToSave)
            else viewModel.updateTask(taskToSave)
        },
        isEdit = taskId != null
    )
}
