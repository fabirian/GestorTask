package com.fabian.gestortask.ui.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fabian.gestortask.ui.navigation.Screen
import com.fabian.gestortask.ui.presentation.tasks.TaskViewModel
import com.fabian.gestortask.ui.presentation.tasks.components.BottomNavBar
import com.fabian.gestortask.ui.utils.AppTopBar
import com.fabian.gestortask.ui.utils.RequireAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTaskListScreen(
    navController: NavHostController,
    viewModelList: TaskListViewModel = hiltViewModel(),
    viewModelTask: TaskViewModel = hiltViewModel()
) {
    RequireAuth(navController){
        val taskLists by remember { derivedStateOf { viewModelList.taskLists } }
        val allTasks by remember { derivedStateOf { viewModelTask.tasks } }

        var expandedListId by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            viewModelList.loadLists()
            viewModelTask.loadTasks()
        }

        Scaffold(

            bottomBar = { BottomNavBar(navController) },
            topBar = {
                AppTopBar(
                    title = { Text("Mis Listas") },
                    onSettingsClick = { navController.navigate(Screen.Configuracion.route)
                    }
                )
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
                items(taskLists.size) { index ->
                    val list = taskLists[index]
                    val isExpanded = expandedListId == list.id
                    val tasksInList = allTasks.filter { it.listId == list.id }

                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = list.name,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                IconButton(onClick = {
                                    expandedListId = if (isExpanded) null else list.id
                                }) {
                                    Icon(
                                        imageVector = if (isExpanded)
                                            Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = "Expandir"
                                    )
                                }
                            }

                            if (isExpanded) {
                                tasksInList.forEach { task ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(
                                                task.title,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                            Text(
                                                task.description,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                        Row {
                                            IconButton(onClick = {
                                                viewModelTask.deleteTask(task.id)
                                            }) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = "Eliminar"
                                                )
                                            }
                                            IconButton(onClick = {
                                                navController.navigate(
                                                    Screen.EditTask.createRoute(
                                                        task.id
                                                    )
                                                )
                                            }) {
                                                Icon(
                                                    Icons.Default.Edit,
                                                    contentDescription = "Editar"
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(onClick = {
                                    viewModelTask.changeList(list.id)
                                    navController.navigate(Screen.AddTask.route)
                                }) {
                                    Text("Añadir tarea")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
