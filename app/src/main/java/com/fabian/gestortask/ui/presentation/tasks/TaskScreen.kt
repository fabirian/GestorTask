package com.fabian.gestortask.ui.presentation.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fabian.gestortask.domain.model.Task

@Composable
fun TaskScreen(
    navController: NavController,
    taskId: Int?,
    viewModel: TaskViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(taskId) {
        if (taskId != null) {
            val task = viewModel.tasks.find { it.id == taskId }
            task?.let {
                title = it.title
                description = it.description
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {
                val task = Task(
                    id = taskId ?: 0,
                    title = title,
                    description = description,
                    isDone = false
                )

                if (taskId == null) {
                    viewModel.addTask(task)
                } else {
                    viewModel.updateTask(task)
                }

                navController.popBackStack()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(if (taskId == null) "Agregar tarea" else "Actualizar tarea")
        }
    }
}

