package com.fabian.gestortask.ui.presentation.tasks.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.fabian.gestortask.ui.navigation.Screen

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.inversePrimary) {
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Tareas") },
            label = { Text("Listas") },
            selected = false,
            onClick = { navController.navigate(Screen.ListTaskList.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = false,
            onClick = { navController.navigate(Screen.List.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = false,
            onClick = { navController.navigate(Screen.Perfil.route) }
        )
    }
}
