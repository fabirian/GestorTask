package com.fabian.gestortask.ui.presentation.tasks.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fabian.gestortask.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableTaskItem(
    task: Task,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onComplete: (Task) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                val completedTask = task.copy(isDone = true)
                onComplete(completedTask)
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completar",
                    tint = Color.White
                )
            }
        },
        content = {
            TaskItem(
                title = task.title,
                tag = task.tag,
                tagColor = task.tagColor,
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    )
}
