package com.fabian.gestortask.domain.usecases.colorlabel

import com.fabian.gestortask.domain.model.ColorLabel
import com.fabian.gestortask.domain.repository.ColorLabelRepository

class SaveAllLabels (private val repository: ColorLabelRepository)  {
    suspend operator fun invoke(labels: List<ColorLabel>) {
        repository.saveAllLabels(labels)
    }
}
