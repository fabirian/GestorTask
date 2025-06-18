package com.fabian.gestortask.domain.usecases.colorlabel

import com.fabian.gestortask.domain.repository.ColorLabelRepository

class DeleteColorLabel (private val repository: ColorLabelRepository)  {
    suspend operator fun invoke(hex: String) {
        repository.deleteColorLabel(hex)
    }
}
