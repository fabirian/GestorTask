package com.fabian.gestortask.domain.usecases.colorlabel

import com.fabian.gestortask.domain.repository.ColorLabelRepository

class EditColorLabel (private val repository: ColorLabelRepository) {
    suspend operator fun invoke(hex: String, newLabel: String) {
        repository.editColorLabel(hex, newLabel)
    }
}
