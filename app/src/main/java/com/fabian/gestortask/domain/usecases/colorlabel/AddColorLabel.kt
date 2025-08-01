package com.fabian.gestortask.domain.usecases.colorlabel

import com.fabian.gestortask.domain.model.ColorLabel
import com.fabian.gestortask.domain.repository.ColorLabelRepository

class AddColorLabel (private val repository: ColorLabelRepository)  {
    suspend operator fun invoke(newColor: ColorLabel) {
        repository.addColorLabel(newColor)
    }
}
