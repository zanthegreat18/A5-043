package com.tugas.klinikhewan.ui.viewmodel.pasien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.tugas.klinikhewan.model.Pasien
import com.tugas.klinikhewan.repository.PasienRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState{
    data class Success(val pasien: List<Pasien>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeViewModel (
    private val psn: PasienRepository
) : ViewModel() {
    var psnUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPsn()
    }


    fun getPsn(){
        viewModelScope.launch {
            psnUiState = HomeUiState.Loading
            psnUiState = try {
                HomeUiState.Success(psn.getAllPasien().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deletePsn(idhewan: String){
        viewModelScope.launch {
            try {
                psn.deletePasien(idhewan)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}