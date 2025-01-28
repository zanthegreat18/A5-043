package com.tugas.klinikhewan.ui.viewmodel.jenis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.tugas.klinikhewan.model.Jenis
import com.tugas.klinikhewan.repository.JenisRepository
import com.tugas.klinikhewan.ui.navigation.DestinasiDetailJenis
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailJnsUiState{
    data class Success(val jenis: Jenis): DetailJnsUiState()
    object Error: DetailJnsUiState()
    object Loading: DetailJnsUiState()
}

class DetailJenisViewModel(
    savedStateHandle: SavedStateHandle,
    private val jns: JenisRepository
): ViewModel(){

    var jenisDetailState: DetailJnsUiState by mutableStateOf(DetailJnsUiState.Loading)
        private set

    private val _idJenishewan: String = checkNotNull(savedStateHandle[DestinasiDetailJenis.idJENIS])


    init {
        getJenisbyId()
    }


    fun getJenisbyId(){
        viewModelScope.launch {
            jenisDetailState = DetailJnsUiState.Loading
            jenisDetailState = try {
                val jenis = jns.getJenisById(_idJenishewan)
                DetailJnsUiState.Success(jenis)
            } catch (e: IOException) {
                DetailJnsUiState.Error
            } catch (e: HttpException){
                DetailJnsUiState.Error
            }
        }
    }
}