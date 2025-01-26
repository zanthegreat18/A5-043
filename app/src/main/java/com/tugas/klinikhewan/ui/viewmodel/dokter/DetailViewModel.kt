package com.tugas.klinikhewan.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.tugas.klinikhewan.model.Dokter
import com.tugas.klinikhewan.model.Pasien
import com.tugas.klinikhewan.repository.DokterRepository
import com.tugas.klinikhewan.repository.PasienRepository
import com.tugas.klinikhewan.ui.navigation.DestinasiDetailDokter
import com.tugas.klinikhewan.ui.navigation.DesttinasiDetailPasien
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailDktrUiState{
    data class Success(val dokter: Dokter): DetailDktrUiState()
    object Error : DetailDktrUiState()
    object Loading : DetailDktrUiState()
}

class DetailDokterViewModel(
    savedStateHandle: SavedStateHandle,
    private val dktr: DokterRepository

) : ViewModel(){

    var dokterDetailState: DetailDktrUiState by mutableStateOf(DetailDktrUiState.Loading)
        private set

    private val _iddokter: String = checkNotNull(savedStateHandle[DestinasiDetailDokter.idDOKTER])

    init {
        getDokterbyId()
    }

    fun getDokterbyId(){
        viewModelScope.launch {
            dokterDetailState = DetailDktrUiState.Loading
            dokterDetailState = try {
                val dokter = dktr.getDokterById(_iddokter)
                DetailDktrUiState.Success(dokter)
            } catch (e: IOException) {
                DetailDktrUiState.Error
            } catch (e: HttpException){
                DetailDktrUiState.Error
            }
        }
    }
}