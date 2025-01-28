package com.tugas.klinikhewan.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.klinikhewan.model.Dokter
import com.tugas.klinikhewan.repository.DokterRepository
import kotlinx.coroutines.launch

class InsertDokterViewModel( private val dktr: DokterRepository): ViewModel(){
    var uiStateDktr by mutableStateOf(InsertDktrUiState())
        private set

    fun updateInsertDktrState(insertDktrUiEvent: InsertDktrUiEvent){
        uiStateDktr = InsertDktrUiState(
            insertDktrUiEvent = insertDktrUiEvent
        )
    }

    suspend fun insertDokter(){
        viewModelScope.launch {
            try {
                dktr.insertDokter(uiStateDktr.insertDktrUiEvent.toDokter())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertDktrUiState(
    val insertDktrUiEvent: InsertDktrUiEvent = InsertDktrUiEvent()
)

data class InsertDktrUiEvent(
    val iddokter: String = "",
    val namadokter: String = "",
    val kontak: String = "",
    val spesialisasi: String = ""
)

fun InsertDktrUiEvent.toDokter(): Dokter = Dokter(
    iddokter = iddokter,
    namadokter = namadokter,
    kontak = kontak,
    spesialisasi = spesialisasi
)

fun Dokter.toInsertDktrUiEvent(): InsertDktrUiEvent = InsertDktrUiEvent(
    iddokter = iddokter,
    namadokter = namadokter,
    kontak = kontak,
    spesialisasi = spesialisasi
)

fun Dokter.toUiStateDktr(): InsertDktrUiState = InsertDktrUiState(
    insertDktrUiEvent = toInsertDktrUiEvent()
)

