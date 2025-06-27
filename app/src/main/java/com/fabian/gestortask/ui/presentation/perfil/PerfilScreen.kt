package com.fabian.gestortask.ui.presentation.perfil

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fabian.gestortask.domain.model.TaskList
import com.fabian.gestortask.ui.navigation.Screen
import com.fabian.gestortask.ui.presentation.tasks.ColorLabelViewModel
import com.fabian.gestortask.ui.presentation.tasks.TaskViewModel
import com.fabian.gestortask.ui.presentation.tasks.components.BottomNavBar
import com.fabian.gestortask.ui.utils.AppTopBar
import com.fabian.gestortask.ui.utils.ColorLabels
import com.fabian.gestortask.ui.utils.RequireAuth
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PerfilScreen(
    navController: NavHostController,
    viewModel: TaskViewModel = hiltViewModel(),
    colorLabelViewModel: ColorLabelViewModel = hiltViewModel()
)
{
    RequireAuth(navController) {
        val user = remember { FirebaseAuth.getInstance().currentUser }
        val userId = user?.uid

        val userLists = viewModel.userLists
        val defaultListId = viewModel.defaultListId
        val colorLabels = colorLabelViewModel.labels

        var expanded by remember { mutableStateOf(false) }
        var selectedList by remember { mutableStateOf<TaskList?>(null) }

        var selectedColorHex by remember { mutableStateOf("") }
        var newColorLabel by remember { mutableStateOf("") }

        LaunchedEffect(userId) {
            userId?.let {
                viewModel.getUserLists()
                viewModel.loadDefaultListId(it)
                colorLabelViewModel.load()
            }
        }

        LaunchedEffect(defaultListId, userLists) {
            selectedList = userLists.find { it.id == defaultListId }
        }

        Scaffold(
            bottomBar = { BottomNavBar(navController) },
            topBar = {
                AppTopBar(
                    title = { Text("Mis Listas") },
                    onSettingsClick = { navController.navigate(Screen.Configuracion.route)
                    }
                )
            }
        )
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
                Spacer(modifier = Modifier.height(32.dp))

                Text("Editar nombres de etiquetas:", style = MaterialTheme.typography.titleMedium)

                colorLabels.forEach { colorLabel ->
                    var editableLabel by remember { mutableStateOf(colorLabel.label) }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color(android.graphics.Color.parseColor(colorLabel.colorHex)))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = editableLabel,
                            onValueChange = {
                                editableLabel = it
                                colorLabelViewModel.updateLocalLabel(colorLabel.colorHex, it)
                            },
                            label = { Text("Etiqueta") },
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            colorLabelViewModel.deleteLabel(colorLabel.colorHex)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar color")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Agregar nuevo color:", style = MaterialTheme.typography.titleMedium)
                Text("Selecciona un color:", style = MaterialTheme.typography.bodyMedium)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ColorLabels.predefinedColors.forEach { hex ->
                        val color = Color(android.graphics.Color.parseColor(hex))
                        val isUsed = colorLabels.any { it.colorHex == hex }

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(color, MaterialTheme.shapes.small)
                                .border(
                                    width = if (selectedColorHex == hex) 3.dp else 1.dp,
                                    color = if (selectedColorHex == hex) Color.Black else Color.Gray,
                                    shape = MaterialTheme.shapes.small
                                )
                                .let {
                                    if (!isUsed) it.clickable { selectedColorHex = hex } else it
                                }
                        )
                    }
                }

                OutlinedTextField(
                    value = newColorLabel,
                    onValueChange = { newColorLabel = it },
                    label = { Text("Etiqueta") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (selectedColorHex.isNotBlank() && newColorLabel.isNotBlank()) {
                            colorLabelViewModel.addLabel(selectedColorHex, newColorLabel)
                            selectedColorHex = ""
                            newColorLabel = ""
                        }
                    },
                    enabled = selectedColorHex.isNotBlank() && newColorLabel.isNotBlank()
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar color")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Agregar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { colorLabelViewModel.saveAllLabels() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Guardar etiquetas")
                }
            }
        }
    }
}
