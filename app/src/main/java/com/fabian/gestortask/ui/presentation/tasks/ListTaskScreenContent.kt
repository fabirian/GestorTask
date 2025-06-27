package com.fabian.gestortask.ui.presentation.tasks

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.ui.presentation.tasks.components.SwipeableTaskItem
import com.fabian.gestortask.ui.presentation.tasks.components.TaskItem
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTaskScreenContent(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    onCompleteClick: (Task) -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val haptic = LocalHapticFeedback.current
    var showCompleted by remember { mutableStateOf(false) }

    // Reorderable list
    val lazyListState = rememberLazyListState()
    val reorderableState = rememberReorderableLazyListState(lazyListState) { from, to ->
        val updatedList = tasks.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        viewModel.reorderTasks(updatedList)
        haptic.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
    }

    // Dropdown menu de etiquetas
    val tagOptions = tasks.mapNotNull { it.tag.takeIf { it.isNotBlank() } }.distinct()
    var tagExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        Text("Tareas activas", style = MaterialTheme.typography.titleMedium)

        // Búsqueda
        TextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.searchQuery = it },
            label = { Text("Buscar...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Filtro por estado
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            TextButton(onClick = { viewModel.showOnlyCompleted = null }) {
                Text("Todos")
            }
            TextButton(onClick = { viewModel.showOnlyCompleted = false }) {
                Text("Activos")
            }
            TextButton(onClick = { viewModel.showOnlyCompleted = true }) {
                Text("Completados")
            }
        }

        // Filtro por etiqueta
        ExposedDropdownMenuBox(
            expanded = tagExpanded,
            onExpandedChange = { tagExpanded = !tagExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.selectedTag,
                onValueChange = {},
                readOnly = true,
                label = { Text("Filtrar por etiqueta") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tagExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            DropdownMenu(
                expanded = tagExpanded,
                onDismissRequest = { tagExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Todas las etiquetas") },
                    onClick = {
                        viewModel.selectedTag = ""
                        tagExpanded = false
                    }
                )
                tagOptions.forEach { tag ->
                    DropdownMenuItem(
                        text = { Text(tag) },
                        onClick = {
                            viewModel.selectedTag = tag
                            tagExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de tareas (reordenables)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState
        ) {
            items(tasks, key = { it.id }) { task ->
                ReorderableItem(reorderableState, key = task.id) { isDragging ->
                    val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)
                    Surface(tonalElevation = elevation) {
                        SwipeableTaskItem(
                            task = task,
                            onEdit = { onEditClick(task.id) },
                            onDelete = { onDeleteClick(task.id) },
                            onComplete = { onCompleteClick(task) },
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .draggableHandle(
                                    onDragStarted = {
                                        haptic.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
                                    },
                                    onDragStopped = {
                                        haptic.performHapticFeedback(HapticFeedbackType.GestureEnd)
                                    }
                                )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar completadas
        TextButton(onClick = { showCompleted = !showCompleted }) {
            Text(if (showCompleted) "Ocultar completadas" else "Ver completadas")
        }

        if (showCompleted) {
            Text("Tareas completadas", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(tasks.filter { it.isDone }) { task ->
                    TaskItem(
                        title = task.title,
                        tag = task.tag,
                        tagColor = task.tagColor,
                        onEdit = {},
                        onDelete = { onDeleteClick(task.id) }
                    )
                }
            }
        }
    }
}
