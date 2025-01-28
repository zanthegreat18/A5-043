package com.tugas.klinikhewan.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.klinikhewan.repository.DokterRepository
import com.tugas.klinikhewan.ui.navigation.UpdateDokter
import kotlinx.coroutines.launch

class UpdateDokterViewModel(
    savedStateHandle: SavedStateHandle,
    private val dktr: DokterRepository
): ViewModel(){
    var updateDktrUiState by mutableStateOf(InsertDktrUiState())
        private set

    private val _iddokter: String = checkNotNull(savedStateHandle[UpdateDokter.idDOKTER])

    init {
        viewModelScope.launch {
            updateDktrUiState = dktr.getDokterById(_iddokter)
                .toUiStateDktr()
        }
    }

    fun updateInsertDktrState(insertDktrUiEvent: InsertDktrUiEvent){
        updateDktrUiState = InsertDktrUiState(
            insertDktrUiEvent = insertDktrUiEvent
        )
    }

    suspend fun updateDokter(){
        viewModelScope.launch {
            try {
                dktr.updateDokter(_iddokter, updateDktrUiState.insertDktrUiEvent.toDokter())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}