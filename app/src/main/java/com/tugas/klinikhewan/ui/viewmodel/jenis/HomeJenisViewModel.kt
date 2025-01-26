package com.tugas.klinikhewan.ui.viewmodel.jenis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.tugas.klinikhewan.model.Jenis
import com.tugas.klinikhewan.repository.JenisRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeJenisUiState{
    data class Success( val jenis: List<Jenis>): HomeJenisUiState()
    object Error : HomeJenisUiState()
    object Loading : HomeJenisUiState()
}

class HomeJenisViewModel( private val jns: JenisRepository) : ViewModel() {
    var jnsUiState: HomeJenisUiState by mutableStateOf(HomeJenisUiState.Loading)
        private set




    fun getJns(){
        viewModelScope.launch {
            jnsUiState = HomeJenisUiState.Loading
            jnsUiState = try {
                HomeJenisUiState.Success(jns.getAllJenis().data)
            } catch (e: IOException) {
                HomeJenisUiState.Error
            } catch (e: HttpException) {
                HomeJenisUiState.Error
            }
        }
    }

    fun deleteJns(idjenishewan: String){
        viewModelScope.launch {
            try {
                jns.deleteJenis(idjenishewan)
            } catch (e: IOException) {
                HomeJenisUiState.Error
            } catch (e: HttpException) {
                HomeJenisUiState.Error
            }
        }
    }
}