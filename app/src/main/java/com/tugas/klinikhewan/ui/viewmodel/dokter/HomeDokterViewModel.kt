package com.tugas.klinikhewan.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.tugas.klinikhewan.model.Dokter
import com.tugas.klinikhewan.repository.DokterRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeDktrUiState{
    data class Success(val dokter: List<Dokter>): HomeDktrUiState()
    object Error : HomeDktrUiState()
    object Loading : HomeDktrUiState()
}


class HomeDokterViewModel ( private val dktr: DokterRepository): ViewModel(){
    var dktrUiState: HomeDktrUiState by mutableStateOf(HomeDktrUiState.Loading)
        private set

    init {
        getDktr()
    }

    fun getDktr(){
        viewModelScope.launch {
            dktrUiState = HomeDktrUiState.Loading
            dktrUiState = try {
                HomeDktrUiState.Success(dktr.getAllDokter().data)
            } catch (e: IOException) {
                HomeDktrUiState.Error
            } catch (e: HttpException){
                HomeDktrUiState.Error
            }
        }
    }

    fun deleteDktr(id: String){
        viewModelScope.launch {
            try {
                dktr.deleteDokter(id)
            } catch (e: IOException) {
                HomeDktrUiState.Error
            } catch (e: HttpException){
                HomeDktrUiState.Error
            }
        }
    }
}