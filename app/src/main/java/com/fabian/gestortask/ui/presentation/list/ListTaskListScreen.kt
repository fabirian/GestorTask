package com.fabian.gestortask.ui.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fabian.gestortask.ui.navigation.Screen
import com.fabian.gestortask.ui.presentation.tasks.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTaskListScreen(
    navController: NavHostController,
    viewModelList: TaskListViewModel = hiltViewModel()
) {

    val taskList = viewModelList.taskLists

    LaunchedEffect(Unit) {
        viewModelList.loadLists()
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        topBar = {
            TopAppBar(title = { Text("Mis listas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddTaskList.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar lista")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(taskList.size) { index ->
                val list = taskList[index]
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = list.name, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Toca para ver tareas",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

