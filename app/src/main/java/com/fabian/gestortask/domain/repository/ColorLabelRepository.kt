package com.fabian.gestortask.domain.repository

import com.fabian.gestortask.domain.model.ColorLabel

interface ColorLabelRepository {
    suspend fun getColorLabels(): List<ColorLabel>
    suspend fun addColorLabel(newColor: ColorLabel)
    suspend fun deleteColorLabel(hex: String)
    suspend fun editColorLabel(hex: String, newLabel: String)
    suspend fun saveAllLabels(labels: List<ColorLabel>)
}