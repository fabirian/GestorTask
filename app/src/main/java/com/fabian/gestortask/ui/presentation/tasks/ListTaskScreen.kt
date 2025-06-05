package com.fabian.gestortask.ui.presentation.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ListTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
){
    val tasks = viewModel.tasks

    LaunchedEffect (Unit) {
        viewModel.loadTasks()
    }

    Column (modifier = Modifier.fillMaxSize()) {
        Text("Lista de tareas", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(tasks.size) { task ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable{
                            navController.navigate("task/${tasks[task].id}")
                        }
                ) {
                    Text(tasks[task].title)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {viewModel.deleteTask(task)}){
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {navController.navigate("task_screen")},
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar tarea")
        }
    }
}

