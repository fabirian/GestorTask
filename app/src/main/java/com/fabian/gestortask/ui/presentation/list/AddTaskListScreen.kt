package com.fabian.gestortask.ui.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fabian.gestortask.domain.model.TaskList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskListScreen(
    navController: NavController,
    taskListId: String?,
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val isTaskSaved = viewModel.isTaskSaved
    val task = viewModel.currentTask

    var name by remember { mutableStateOf("") }

    LaunchedEffect(taskListId) {
        if (taskListId != null) {
            viewModel.loadLists()
        }
    }

    LaunchedEffect(task) {
        task?.let {
            name = it.name
        }
    }

    LaunchedEffect(isTaskSaved) {
        if (isTaskSaved) {
            navController.popBackStack()
            viewModel.resetSaveState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (taskListId != null) "Editar lista" else "Nueva lista")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de la lista") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val taskToSave = TaskList(
                        id = taskListId ?: "",
                        name = name,
                        userId = task?.userId ?: ""
                    )

                    if (taskListId == null) {
                        viewModel.addList(taskToSave)
                    } else {
                        viewModel.updateList(taskToSave)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (taskListId != null) "Actualizar lista" else "Crear lista")
            }
        }
    }
}
