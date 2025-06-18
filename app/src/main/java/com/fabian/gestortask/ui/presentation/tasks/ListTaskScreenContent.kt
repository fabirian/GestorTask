package com.fabian.gestortask.ui.presentation.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fabian.gestortask.domain.model.Task
import com.fabian.gestortask.ui.presentation.tasks.components.SwipeableTaskItem
import com.fabian.gestortask.ui.presentation.tasks.components.TaskItem

@Composable
fun ListTaskScreenContent(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    onCompleteClick: (Task) -> Unit
) {
    var showCompleted by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Tareas activas",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(tasks.filter { !it.isDone }) { task ->
                SwipeableTaskItem(
                    task = task,
                    onEdit = { onEditClick(task.id) },
                    onDelete = { onDeleteClick(task.id) },
                    onComplete = { onCompleteClick(task.copy(isDone = true)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { showCompleted = !showCompleted }) {
            Text(if (showCompleted) "Ocultar completadas" else "Ver completadas")
        }

        if (showCompleted) {
            Text(
                text = "Tareas completadas",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

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
