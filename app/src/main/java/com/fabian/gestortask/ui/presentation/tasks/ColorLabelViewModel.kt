package com.fabian.gestortask.ui.presentation.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabian.gestortask.domain.model.ColorLabel
import com.fabian.gestortask.domain.usecases.colorlabel.ColorUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorLabelViewModel @Inject constructor(
    private val colorUseCase: ColorUseCase
) : ViewModel() {

    var labels by mutableStateOf<List<ColorLabel>>(emptyList())

    fun load() {
        viewModelScope.launch {
            labels = colorUseCase.getColorLabels()
        }
    }

    fun addLabel(colorHex: String, label: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val newColor = ColorLabel(uid, colorHex, label)
        viewModelScope.launch {
            colorUseCase.addColorLabel(newColor)
            load()
        }
    }

    fun deleteLabel(colorHex: String) {
        viewModelScope.launch {
            colorUseCase.deleteColorLabel(colorHex)
            load()
        }
    }

    fun editLabel(colorHex: String, newLabel: String) {
        viewModelScope.launch {
            colorUseCase.editColorLabel(colorHex, newLabel)
            load()
        }
    }

    fun saveAllLabels() {
        viewModelScope.launch {
            colorUseCase.saveAllLabels(labels)
            load()
        }
    }

    fun updateLocalLabel(hex: String, newLabel: String) {
        labels = labels.map {
            if (it.colorHex == hex) it.copy(label = newLabel) else it
        }
    }
}
