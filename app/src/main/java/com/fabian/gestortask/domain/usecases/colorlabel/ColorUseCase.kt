package com.fabian.gestortask.domain.usecases.colorlabel

data class ColorUseCase (
    val getColorLabels: GetColorLabels,
    val addColorLabel: AddColorLabel,
    val deleteColorLabel: DeleteColorLabel,
    val editColorLabel: EditColorLabel,
    val saveAllLabels: SaveAllLabels
)