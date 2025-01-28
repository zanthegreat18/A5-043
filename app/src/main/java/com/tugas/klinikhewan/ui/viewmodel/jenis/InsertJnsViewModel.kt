package com.tugas.klinikhewan.ui.viewmodel.jenis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.klinikhewan.model.Jenis
import com.tugas.klinikhewan.repository.JenisRepository
import kotlinx.coroutines.launch

class InsertJnsViewModel( private val jns: JenisRepository): ViewModel(){
    var uiStateJns by mutableStateOf(InsertJnsUiState())
        private set

    fun updateInsertJnsState(insertJnsUiEvent: InsertJnsUiEvent){
        uiStateJns = InsertJnsUiState(
            insertJnsUiEvent = insertJnsUiEvent
        )
    }

    suspend fun insertJns(){
        viewModelScope.launch {
            try {
                jns.insertJenis(uiStateJns.insertJnsUiEvent.toJenis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}




data class InsertJnsUiState(
    val insertJnsUiEvent: InsertJnsUiEvent = InsertJnsUiEvent(),
)

data class InsertJnsUiEvent(
    val idjenishewan: String = "",
    val namajenishewan: String = "",
    val deskripsi: String = ""
)

fun InsertJnsUiEvent.toJenis(): Jenis = Jenis(
    idjenishewan = idjenishewan,
    namajenishewan = namajenishewan,
    deskripsi = deskripsi
)

//UntukJnsEvent
fun Jenis.toInsertJenisUiEvent(): InsertJnsUiEvent = InsertJnsUiEvent(
    idjenishewan = idjenishewan,
    namajenishewan = namajenishewan,
    deskripsi = deskripsi
)

fun Jenis.toUiStateJns(): InsertJnsUiState = InsertJnsUiState(
    insertJnsUiEvent = toInsertJenisUiEvent()
)



