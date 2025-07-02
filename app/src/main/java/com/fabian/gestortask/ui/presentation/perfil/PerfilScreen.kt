package com.fabian.gestortask.ui.presentation.perfil

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PerfilScreen(
    navController: NavHostController,
    viewModel: TaskViewModel = hiltViewModel(),
    colorLabelViewModel: ColorLabelViewModel = hiltViewModel()
) {
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

        var showEditDialog by remember { mutableStateOf(false) }
        var colorToEdit by remember { mutableStateOf("") }
        var labelToEdit by remember { mutableStateOf("") }

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

        if (showEditDialog) {
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                title = { Text("Editar etiqueta") },
                text = {
                    Column {
                        Text("Color:")
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(colorToEdit.toColorInt()), CircleShape)
                                .border(2.dp, Color.Black, CircleShape)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = labelToEdit,
                            onValueChange = { labelToEdit = it },
                            label = { Text("Nombre") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        colorLabelViewModel.updateLocalLabel(colorToEdit, labelToEdit)
                        showEditDialog = false
                    }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        Scaffold(
            bottomBar = { BottomNavBar(navController) },
            topBar = {
                AppTopBar(
                    title = { Text("Mis Listas") },
                    onSettingsClick = { navController.navigate(Screen.Configuracion.route) }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    Text("Configuración de listas", style = MaterialTheme.typography.headlineSmall)
                }

                // --- Lista por defecto ---
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Lista por defecto", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))

                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded }
                            ) {
                                OutlinedTextField(
                                    value = selectedList?.name ?: "",
                                    onValueChange = {},
                                    readOnly = true,
                                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                                    label = { Text("Seleccionar lista") },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }
                                )

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

                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Número de listas: ${userLists.size}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                // --- Etiquetas existentes (ícono para editar) ---
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Editar etiquetas existentes", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))

                            colorLabels.forEach { colorLabel ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .background(Color(colorLabel.colorHex.toColorInt()), CircleShape)
                                    )
                                    Spacer(Modifier.width(8.dp))

                                    Text(
                                        text = colorLabel.label,
                                        modifier = Modifier.weight(1f)
                                    )

                                    IconButton(onClick = {
                                        colorToEdit = colorLabel.colorHex
                                        labelToEdit = colorLabel.label
                                        showEditDialog = true
                                    }) {
                                        Icon(Icons.Default.Add, contentDescription = "Editar")
                                    }

                                    IconButton(onClick = {
                                        colorLabelViewModel.deleteLabel(colorLabel.colorHex)
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
// --- Agregar nueva etiqueta ---
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Agregar nueva etiqueta", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            Text("Selecciona un color", style = MaterialTheme.typography.bodyMedium)

                            var colorMenuExpanded by remember { mutableStateOf(false) }

                            Box {
                                Button(
                                    onClick = { colorMenuExpanded = true },
                                    modifier = Modifier.size(48.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = if (selectedColorHex.isNotBlank()) Color(selectedColorHex.toColorInt()) else Color.Gray),
                                    shape = CircleShape,
                                    contentPadding = PaddingValues(0.dp)
                                ) {}

                                DropdownMenu(
                                    expanded = colorMenuExpanded,
                                    onDismissRequest = { colorMenuExpanded = false }
                                ) {
                                    ColorLabels.predefinedColors.forEach { hex ->
                                        val color = Color(hex.toColorInt())
                                        val isUsed = colorLabels.any { it.colorHex == hex }

                                        DropdownMenuItem(
                                            text = {},
                                            leadingIcon = {
                                                Box(
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                        .background(color, CircleShape)
                                                )
                                            },
                                            onClick = {
                                                if (!isUsed) {
                                                    selectedColorHex = hex
                                                    colorMenuExpanded = false
                                                }
                                            },
                                            enabled = !isUsed
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedTextField(
                                    value = newColorLabel,
                                    onValueChange = { newColorLabel = it },
                                    label = { Text("Nombre de etiqueta") },
                                    modifier = Modifier.weight(1f),
                                    singleLine = true
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        colorLabelViewModel.addLabel(
                                            selectedColorHex,
                                            newColorLabel
                                        )
                                        selectedColorHex = ""
                                        newColorLabel = ""
                                    },
                                    enabled = selectedColorHex.isNotBlank() && newColorLabel.isNotBlank()
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                                }
                            }
                        }
                    }
                }


                // --- Guardar cambios ---
                item {
                    Button(
                        onClick = { colorLabelViewModel.saveAllLabels() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar etiquetas")
                    }
                }
            }
        }
    }
}