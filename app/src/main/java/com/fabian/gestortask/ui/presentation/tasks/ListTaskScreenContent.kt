package com.fabian.gestortask.ui.presentation.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fabian.gestortask.domain.model.Task

@Composable
fun ListTaskScreenContent(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay tareas registradas.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(tasks.size) { index ->
                    val task = tasks[index]
                    TaskItem(
                        title = task.title,
                        onEdit = { onEditClick(task.id) },
                        onDelete = { onDeleteClick(task.id) }
                    )
                }
            }
        }
    }
}
