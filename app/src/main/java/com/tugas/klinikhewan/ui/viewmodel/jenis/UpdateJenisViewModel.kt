package com.tugas.klinikhewan.ui.viewmodel.jenis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.klinikhewan.repository.JenisRepository
import com.tugas.klinikhewan.ui.navigation.UpdateJenis
import kotlinx.coroutines.launch

class UpdateJenisViewModel(
    savedStateHandle: SavedStateHandle,
    private val jns: JenisRepository
): ViewModel(){
    var updateJnsUiState by mutableStateOf(InsertJnsUiState())
        private set

    private val _idJenishewan: String = checkNotNull(savedStateHandle[UpdateJenis.idJENIS])

    init {
        viewModelScope.launch {
            updateJnsUiState = jns.getJenisById(_idJenishewan)
                .toUiStateJns()
        }
    }

    fun updateInsertJnsState(insertJnsUiEvent: InsertJnsUiEvent){
        updateJnsUiState = InsertJnsUiState(
            insertJnsUiEvent = insertJnsUiEvent
        )
    }

    suspend fun updateJenis(){
        viewModelScope.launch {
            try {
                jns.updateJenis(_idJenishewan, updateJnsUiState.insertJnsUiEvent.toJenis())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}