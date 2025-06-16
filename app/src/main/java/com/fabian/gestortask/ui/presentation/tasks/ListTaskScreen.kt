package com.fabian.gestortask.ui.presentation.tasks


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fabian.gestortask.ui.navigation.Screen
import com.fabian.gestortask.ui.presentation.tasks.components.BottomNavBar

@Composable
fun ListTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val tasks = viewModel.tasks

    LaunchedEffect(Unit) {
        viewModel.loadDefaultListTasks()
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddTask.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar tarea")
            }
        }
    ) { innerPadding ->
        ListTaskScreenContent(
            modifier = Modifier.padding(innerPadding),
            tasks = tasks,
            onEditClick = { taskId -> navController.navigate(Screen.EditTask.createRoute(taskId)) },
            onDeleteClick = { taskId -> viewModel.deleteTask(taskId) },
        )
    }

}
