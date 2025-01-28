package com.tugas.klinikhewan.ui.viewmodel.perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.tugas.klinikhewan.model.Perawatan
import com.tugas.klinikhewan.repository.PerawatanRepository
import com.tugas.klinikhewan.ui.navigation.DestinasiDetailPerawatan
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPrwtnUiState{
    data class Success(val perawatan: Perawatan): DetailPrwtnUiState()
    object Error: DetailPrwtnUiState()
    object Loading: DetailPrwtnUiState()
}

class DetailPerawatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val prwtn: PerawatanRepository
): ViewModel(){

    var perawatanDetailState: DetailPrwtnUiState by mutableStateOf(DetailPrwtnUiState.Loading)
        private set

    private val _idPerawatan: String = checkNotNull(savedStateHandle[DestinasiDetailPerawatan.idPERAWATAN])


    init {
        getPerawatanbyId()
    }


    fun getPerawatanbyId(){
        viewModelScope.launch {
            perawatanDetailState = DetailPrwtnUiState.Loading
            perawatanDetailState = try {
                val perawatan = prwtn.getPerawatanById(_idPerawatan)
                DetailPrwtnUiState.Success(perawatan)
            } catch (e: IOException) {
                DetailPrwtnUiState.Error
            } catch (e: HttpException){
                DetailPrwtnUiState.Error
            }
        }
    }
}