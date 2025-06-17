package com.fabian.gestortask.ui.presentation.perfil

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fabian.gestortask.domain.model.TaskList
import com.fabian.gestortask.ui.navigation.Screen
import com.fabian.gestortask.ui.presentation.tasks.TaskViewModel
import com.fabian.gestortask.ui.presentation.tasks.components.BottomNavBar
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavHostController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val user = remember { FirebaseAuth.getInstance().currentUser }
    val userId = user?.uid

    val userLists = viewModel.userLists
    val defaultListId = viewModel.defaultListId

    var expanded by remember { mutableStateOf(false) }
    var selectedList by remember { mutableStateOf<TaskList?>(null) }

    LaunchedEffect(userId) {
        userId?.let {
            viewModel.getUserLists()
            viewModel.loadDefaultListId(it)
        }
    }

    LaunchedEffect(defaultListId, userLists) {
        selectedList = userLists.find { it.id == defaultListId }
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
        })
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Perfil", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Lista por defecto:")

            Box {
                Button(onClick = { expanded = true }) {
                    Text(selectedList?.name ?: "Seleccionar lista")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    userLists.forEach { list ->
                        DropdownMenuItem(
                            text = { Text(list.name) },
                            onClick = {
                                selectedList = list
                                expanded = false
                                userId?.let { uid ->
                                    viewModel.setDefaultListId(uid, list.id)
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Número de listas: ${userLists.size}")
        }
    }
}
