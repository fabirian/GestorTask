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

@Composable
fun TaskScreen(
    navController: NavController,
    taskId: String?,
    viewModel: TaskViewModel = hiltViewModel(),
    colorLabelViewModel: ColorLabelViewModel = hiltViewModel()
) {
    val isTaskSaved = viewModel.isTaskSaved
    val task = viewModel.currentTask

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    var tagColor by remember { mutableStateOf("") }

    val userId = viewModel.userId
    val colorLabels = colorLabelViewModel.labels

    LaunchedEffect(userId) {
        colorLabelViewModel.load()
    }

    LaunchedEffect(taskId) {
        taskId?.let { viewModel.loadTaskById(it) }
        viewModel.loadDefaultListId(userId)
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
            tag = it.tag
            tagColor = it.tagColor
        }
    }

    if (viewModel.isDefaultListLoaded) {
        TaskForm(
            title = title,
            onTitleChange = { title = it },
            description = description,
            onDescriptionChange = { description = it },
            tag = tag,
            onTagChange = { tag = it },
            tagColor = tagColor,
            onTagColorChange = { tagColor = it },
            onSaveClick = {
                if (taskId == null) {
                    viewModel.addNewTask(title, description, tag, tagColor)
                } else {
                    viewModel.updateTask(
                        task?.copy(
                            title = title,
                            description = description,
                            tag = tag,
                            tagColor = tagColor
                        ) ?: Task(
                            id = "",
                            title = title,
                            description = description,
                            isDone = false,
                            listId = viewModel.defaultListId ?: "",
                            userId = userId,
                            tag = tag,
                            tagColor = tagColor
                        )
                    )
                }
            },
            isEdit = taskId != null,
            colorLabels  = colorLabels.associate { it.colorHex to it.label }
        )
    } else {
        CircularProgressIndicator()
    }
}
