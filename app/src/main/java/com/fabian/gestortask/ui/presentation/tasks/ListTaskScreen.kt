package com.fabian.gestortask.ui.presentation.tasks


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fabian.gestortask.ui.navigation.Screen
import com.fabian.gestortask.ui.presentation.tasks.components.BottomNavBar
import com.fabian.gestortask.ui.utils.AppTopBar
import com.fabian.gestortask.ui.utils.RequireAuth

@Composable
fun ListTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    RequireAuth(navController) {
        val tasks = viewModel.filteredTasks

        LaunchedEffect(Unit) {
            viewModel.loadDefaultListTasks()
        }

        Scaffold(
            topBar = {
                AppTopBar(
                    title = { Text("Mis Tareas") },
                    onSettingsClick = { navController.navigate(Screen.Configuracion.route) }
                )
            },
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
                onCompleteClick = { taskId -> viewModel.markTaskAsCompletedInstant(taskId) }
            )
        }
    }

}
