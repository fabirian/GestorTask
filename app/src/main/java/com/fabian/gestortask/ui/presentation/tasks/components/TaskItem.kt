package com.fabian.gestortask.ui.presentation.tasks.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@Composable
fun TaskItem(
    title: String,
    tag: String,
    tagColor: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    dragHandle: Modifier = Modifier
) {
    val parsedColor = try {
        Color(tagColor.toColorInt())
    } catch (e: Exception) {
        Log.e("TaskItem", "Error al parsear el color", e)
        Color(0xFFE0E0E0)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(parsedColor.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 👇 Este botón será el "handle" para arrastrar la tarea
            IconButton(
                onClick = {},
                modifier = dragHandle.padding(end = 8.dp) // 👈 Se aplica el dragHandle aquí
            ) {
                Icon(
                    imageVector = Icons.Default.DragHandle,
                    contentDescription = "Arrastrar tarea"
                )
            }

            Text(
                text = title,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }

        if (tag.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = tag,
                    color = Color.White
                )
            }
        }
    }
}
