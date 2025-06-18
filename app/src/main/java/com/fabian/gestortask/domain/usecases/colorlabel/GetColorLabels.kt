package com.fabian.gestortask.domain.usecases.colorlabel

import com.fabian.gestortask.domain.repository.ColorLabelRepository

class GetColorLabels (private val repository: ColorLabelRepository) {
    suspend operator fun invoke() = repository.getColorLabels()
}