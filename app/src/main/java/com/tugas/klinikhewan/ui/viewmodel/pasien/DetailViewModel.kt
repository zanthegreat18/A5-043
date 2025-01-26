package com.tugas.klinikhewan.ui.viewmodel.pasien


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.tugas.klinikhewan.model.Pasien
import com.tugas.klinikhewan.repository.PasienRepository
import com.tugas.klinikhewan.ui.navigation.DesttinasiDetailPasien
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPsnUiState{
    data class Success(val pasien: Pasien): DetailPsnUiState()
    object Error : DetailPsnUiState()
    object Loading : DetailPsnUiState()
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn: PasienRepository

) : ViewModel(){

    var pasienDetailState: DetailPsnUiState by mutableStateOf(DetailPsnUiState.Loading)
        private set

    private val _idhewan: String = checkNotNull(savedStateHandle[DesttinasiDetailPasien.idPASIEN])

    init {
        getPasienbyId()
    }

    fun getPasienbyId(){
        viewModelScope.launch {
            pasienDetailState = DetailPsnUiState.Loading
            pasienDetailState = try {
                val pasien = psn.getPasienById(_idhewan)
                DetailPsnUiState.Success(pasien)
            } catch (e: IOException) {
                DetailPsnUiState.Error
            } catch (e: HttpException){
                DetailPsnUiState.Error
            }
        }
    }
}