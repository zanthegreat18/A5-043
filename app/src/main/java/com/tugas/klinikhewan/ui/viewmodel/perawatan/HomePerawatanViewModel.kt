package com.tugas.klinikhewan.ui.viewmodel.perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.tugas.klinikhewan.model.Perawatan
import com.tugas.klinikhewan.repository.PerawatanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePrwtnUiState{
    data class Success(val perawatan: List<Perawatan>): HomePrwtnUiState()
    object Error: HomePrwtnUiState()
    object Loading: HomePrwtnUiState()
}

class HomePerawatanViewModel (
    private val prwtn: PerawatanRepository
) : ViewModel() {
    var prwtnUiState: HomePrwtnUiState by mutableStateOf(HomePrwtnUiState.Loading)
        private set

    init {
        getPrwtn()
    }


    fun getPrwtn(){
        viewModelScope.launch {
            prwtnUiState = HomePrwtnUiState.Loading
            prwtnUiState = try {
                HomePrwtnUiState.Success(prwtn.getAllPerawatan().data)
            } catch (e: IOException) {
                HomePrwtnUiState.Error
            } catch (e: HttpException) {
                HomePrwtnUiState.Error
            }
        }
    }

    fun deletePrwtn(idperawatan: String){
        viewModelScope.launch {
            try {
                prwtn.deletePerawatan(idperawatan)
            } catch (e: IOException) {
                HomePrwtnUiState.Error
            } catch (e: HttpException) {
                HomePrwtnUiState.Error
            }
        }
    }
}