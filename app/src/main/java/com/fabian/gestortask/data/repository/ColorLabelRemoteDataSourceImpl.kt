package com.fabian.gestortask.data.repository

import com.fabian.gestortask.data.remote.ColorLabelRemoteDataSource
import com.fabian.gestortask.domain.model.ColorLabel
import com.fabian.gestortask.domain.repository.ColorLabelRepository

class ColorLabelRepositoryImpl(
    private val remote: ColorLabelRemoteDataSource
) : ColorLabelRepository {

    override suspend fun getColorLabels() = remote.getColorLabels()

    override suspend fun addColorLabel(newColor: ColorLabel) {
        remote.addColorLabel(newColor)
    }

    override suspend fun deleteColorLabel(hex: String) {
        remote.deleteColorLabel(hex)
    }

    override suspend fun editColorLabel(hex: String, newLabel: String) {
        remote.editColorLabel(hex, newLabel)
    }

    override suspend fun saveAllLabels(labels: List<ColorLabel>) {
        remote.saveAllLabels(labels)
    }
}
