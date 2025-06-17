package com.fabian.gestortask.ui.presentation.tasks

import androidx.compose.material3.CircularProgressIndicator
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
import com.google.firebase.auth.FirebaseAuth

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
        viewModel.loadDefaultListId(viewModel.userId)
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

    if (viewModel.isDefaultListLoaded) {
        TaskForm(
            title = title,
            onTitleChange = { title = it },
            description = description,
            onDescriptionChange = { description = it },
            onSaveClick = {
                viewModel.addNewTask(title, description)
            },
            isEdit = false
        )
    } else {
        CircularProgressIndicator()
    }
}
