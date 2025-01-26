package com.tugas.klinikhewan.ui.viewmodel.pasien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.klinikhewan.model.Jenis
import com.tugas.klinikhewan.repository.JenisRepository
import com.tugas.klinikhewan.repository.PasienRepository
import com.tugas.klinikhewan.ui.navigation.UpdatePasien
import kotlinx.coroutines.launch

class UpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn: PasienRepository,
    private val jns: JenisRepository
): ViewModel(){
    var updatePsnUiState by mutableStateOf(InsertPsnUiState())
        private set

    var listJenis by mutableStateOf(listOf<Jenis>())

    private val _idhewan: String = checkNotNull(savedStateHandle[UpdatePasien.idPASIEN])

    init {
        viewModelScope.launch {
            LoadJenis()
            updatePsnUiState = psn.getPasienById(_idhewan)
                .toUiStatePsn()
        }
    }

    fun updateInsertPsnState(insertPsnUiEvent: InsertPsnUiEvent){
        updatePsnUiState = InsertPsnUiState(
            insertPsnUiEvent = insertPsnUiEvent
        )
    }

    suspend fun updatePasien(){
        viewModelScope.launch {
            try {
                psn.updatePasien(_idhewan, updatePsnUiState.insertPsnUiEvent.toPasien())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    fun LoadJenis(){
        viewModelScope.launch {
            try {
                listJenis = jns.getAllJenis().data
            } catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
}