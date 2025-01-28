package com.tugas.klinikhewan.ui.viewmodel.perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.klinikhewan.model.Dokter
import com.tugas.klinikhewan.model.Jenis
import com.tugas.klinikhewan.model.Pasien
import com.tugas.klinikhewan.model.Perawatan
import com.tugas.klinikhewan.repository.DokterRepository
import com.tugas.klinikhewan.repository.JenisRepository
import com.tugas.klinikhewan.repository.PasienRepository
import com.tugas.klinikhewan.repository.PerawatanRepository
import com.tugas.klinikhewan.ui.viewmodel.pasien.InsertPsnUiEvent
import com.tugas.klinikhewan.ui.viewmodel.pasien.InsertPsnUiState
import com.tugas.klinikhewan.ui.viewmodel.pasien.toPasien
import kotlinx.coroutines.launch

class InsertPrwtnViewModel(
    private val psn: PasienRepository,
    private val dktr: DokterRepository,
    private val prwtn: PerawatanRepository
): ViewModel(){
    var uiStatePrwtn by mutableStateOf(InsertPrwtnUiState())
        private set

    var listhewan by mutableStateOf(listOf<Pasien>())
        private set

    var listdokter by mutableStateOf(listOf<Dokter>())
        private set

    init {
        LoadDataList()
    }

    fun updateInsertPrwtnState(insertUiPrwtnEvent: InsertUiPrwtnEvent){
        uiStatePrwtn = InsertPrwtnUiState(
            insertUiPrwtnEvent = insertUiPrwtnEvent
        )
    }

    suspend fun insertPrwtn(){
        viewModelScope.launch {
            try {
                prwtn.insertPerawatan(uiStatePrwtn.insertUiPrwtnEvent.toPrwtn())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun LoadDataList(){
        viewModelScope.launch {
            try {
                listhewan = psn.getAllPasien().data
                listdokter = dktr.getAllDokter().data
            } catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPrwtnUiState(
    val insertUiPrwtnEvent: InsertUiPrwtnEvent = InsertUiPrwtnEvent()
)

data class InsertUiPrwtnEvent(
    val idperawatan: String = "",
    val idhewan: String = "",
    val iddokter: String = "",
    val tanggalperawatan: String = "",
    val detailperawatan: String = ""
)

fun InsertUiPrwtnEvent.toPrwtn(): Perawatan = Perawatan(
    idperawatan = idperawatan,
    idhewan = idhewan,
    iddokter = iddokter,
    tanggalperawatan = tanggalperawatan,
    detailperawatan = detailperawatan
)

fun Perawatan.toUiStatePrwtn(): InsertPrwtnUiState = InsertPrwtnUiState(
    insertUiPrwtnEvent = toInsertUiPrwtnEvent()
)

fun Perawatan.toInsertUiPrwtnEvent(): InsertUiPrwtnEvent = InsertUiPrwtnEvent(
    idperawatan = idperawatan,
    idhewan = idhewan,
    iddokter = iddokter,
    tanggalperawatan = tanggalperawatan,
    detailperawatan = detailperawatan
)

