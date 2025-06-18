package com.fabian.gestortask.ui.presentation.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fabian.gestortask.ui.utils.ColorLabels

@Composable
fun TaskForm(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    tag: String,
    onTagChange: (String) -> Unit,
    tagColor: String,
    onTagColorChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    isEdit: Boolean
) {

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = tag,
            onValueChange = onTagChange,
            label = { Text("Etiqueta (ej: Estudio)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Text(
            text = "Selecciona un color de etiqueta:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ColorLabels.predefinedColors.forEach { hex ->
                val color = Color(android.graphics.Color.parseColor(hex))
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color, CircleShape)
                        .clickable {
                            onTagColorChange(hex)
                            ColorLabels.editableColorLabels[hex]?.let { onTagChange(it) }
                        }
                        .border(
                            width = if (tagColor == hex) 3.dp else 1.dp,
                            color = if (tagColor == hex) Color.Black else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }

        if (tagColor.isNotEmpty()) {
            Text(
                text = "Etiqueta sugerida: ${ColorLabels.editableColorLabels[tagColor] ?: "Personalizada"}",
                modifier = Modifier.padding(top = 2.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Button(
            onClick = onSaveClick,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(if (isEdit) "Actualizar tarea" else "Agregar tarea")
        }
    }
}
