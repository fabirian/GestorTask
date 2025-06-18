package com.fabian.gestortask.ui.utils

import androidx.compose.runtime.mutableStateMapOf

object ColorLabels {
    val editableColorLabels = mutableStateMapOf(
        "#FF6B6B" to "Urgente",
        "#4ECDC4" to "Importante",
        "#FFD93D" to "Advertencia",
        "#1A535C" to "Estudio",
        "#FF9F1C" to "Trabajo",
        "#6A4C93" to "Creatividad",
        "#38B000" to "Opcional"
    )

    fun getLabel(hex: String): String = editableColorLabels[hex] ?: "Sin etiqueta"

    val predefinedColors: List<String>
        get() = editableColorLabels.keys.toList()
}
