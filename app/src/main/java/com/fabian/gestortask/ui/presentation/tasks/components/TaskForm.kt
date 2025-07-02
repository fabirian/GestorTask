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

@OptIn(ExperimentalMaterial3Api::class)
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
    isEdit: Boolean,
    colorLabels: Map<String, String>,
    predefinedTags: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var customTag by remember { mutableStateOf("") }

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

        Text(
            text = "Selecciona o escribe una etiqueta:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )

        // Lista desplegable de etiquetas
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                value = tag,
                onValueChange = {
                    onTagChange(it)
                    customTag = it
                },
                label = { Text("Etiqueta") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = false,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                predefinedTags.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onTagChange(option)
                                customTag = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Text(
            text = "Selecciona un color de etiqueta:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            colorLabels.keys.forEach { hex ->
                val color = Color(android.graphics.Color.parseColor(hex))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color, shape = CircleShape)
                        .border(
                            width = if (tagColor == hex) 3.dp else 1.dp,
                            color = if (tagColor == hex) Color.Black else Color.LightGray,
                            shape = CircleShape
                        )
                        .clickable {
                            onTagColorChange(hex)
                            colorLabels[hex]?.let { onTagChange(it) }
                        }
                )
            }
        }

        if (tagColor.isNotEmpty()) {
            Text(
                text = "Etiqueta sugerida: ${colorLabels[tagColor] ?: "Personalizada"}",
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEdit) "Actualizar tarea" else "Agregar tarea")
        }
    }
}
