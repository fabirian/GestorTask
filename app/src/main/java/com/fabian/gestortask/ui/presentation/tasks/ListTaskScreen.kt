package com.fabian.gestortask.ui.presentation.tasks


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fabian.gestortask.ui.navigation.Screen

@Composable
fun ListTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val tasks = viewModel.tasks

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    ListTaskScreenContent(
        tasks = tasks,
        onAddClick = { navController.navigate(Screen.AddTask.route) },
        onEditClick = { taskId -> navController.navigate(Screen.EditTask.createRoute(taskId)) },
        onDeleteClick = { taskId -> viewModel.deleteTask(taskId) },
        onSettingsClick = { navController.navigate(Screen.Configuracion.route) }
    )
}
