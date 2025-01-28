package com.tugas.klinikhewan.ui.viewmodel.perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.klinikhewan.model.Dokter
import com.tugas.klinikhewan.model.Pasien
import com.tugas.klinikhewan.repository.DokterRepository
import com.tugas.klinikhewan.repository.PasienRepository
import com.tugas.klinikhewan.repository.PerawatanRepository
import com.tugas.klinikhewan.ui.navigation.UpdatePerawatan
import kotlinx.coroutines.launch

class UpdatePerawatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn: PasienRepository,
    private val prwtn: PerawatanRepository,
    private val dktr: DokterRepository
): ViewModel(){
    var updatePrwtnUiState by mutableStateOf(InsertPrwtnUiState())
        private set

    var listDokter by mutableStateOf(listOf<Dokter>())
    var listPasien by mutableStateOf(listOf<Pasien>())

    private val _idperawatan: String = checkNotNull(savedStateHandle[UpdatePerawatan.idPERAWATAN])

    init {
        viewModelScope.launch {
            LoadData()
            updatePrwtnUiState = prwtn.getPerawatanById(_idperawatan)
                .toUiStatePrwtn()
        }
    }

    fun updateInsertPrwtnState(insertUiPrwtnEvent: InsertUiPrwtnEvent){
        updatePrwtnUiState = InsertPrwtnUiState(
            insertUiPrwtnEvent = insertUiPrwtnEvent
        )
    }

    suspend fun updatePerawatan(){
        viewModelScope.launch {
            try {
                prwtn.updatePerawatan(_idperawatan, updatePrwtnUiState.insertUiPrwtnEvent.toPrwtn())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    fun LoadData(){
        viewModelScope.launch {
            try {
                listDokter = dktr.getAllDokter().data
                listPasien = psn.getAllPasien().data
            } catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
}