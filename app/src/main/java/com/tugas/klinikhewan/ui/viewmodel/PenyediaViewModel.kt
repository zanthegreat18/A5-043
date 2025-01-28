package com.tugas.klinikhewan.ui.viewmodel

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tugas.klinikhewan.KlinikHewanApplication
import com.tugas.klinikhewan.ui.viewmodel.dokter.DetailDokterViewModel
import com.tugas.klinikhewan.ui.viewmodel.dokter.HomeDokterViewModel
import com.tugas.klinikhewan.ui.viewmodel.dokter.InsertDokterViewModel
import com.tugas.klinikhewan.ui.viewmodel.dokter.UpdateDokterViewModel
import com.tugas.klinikhewan.ui.viewmodel.jenis.DetailJenisViewModel
import com.tugas.klinikhewan.ui.viewmodel.jenis.HomeJenisViewModel
import com.tugas.klinikhewan.ui.viewmodel.jenis.InsertJnsViewModel
import com.tugas.klinikhewan.ui.viewmodel.jenis.UpdateJenisViewModel
import com.tugas.klinikhewan.ui.viewmodel.pasien.DetailViewModel
import com.tugas.klinikhewan.ui.viewmodel.pasien.HomeViewModel
import com.tugas.klinikhewan.ui.viewmodel.pasien.InsertPsnViewModel
import com.tugas.klinikhewan.ui.viewmodel.pasien.UpdateViewModel
import com.tugas.klinikhewan.ui.viewmodel.perawatan.DetailPerawatanViewModel
import com.tugas.klinikhewan.ui.viewmodel.perawatan.HomePerawatanViewModel
import com.tugas.klinikhewan.ui.viewmodel.perawatan.InsertPrwtnViewModel
import com.tugas.klinikhewan.ui.viewmodel.perawatan.UpdatePerawatanViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiKlinikGw().container.pasienRepository)
        }
        initializer {
            HomeJenisViewModel(aplikasiKlinikGw().container.jenisRepository)
        }
        initializer {
            HomeDokterViewModel(aplikasiKlinikGw().container.dokterRepository)
        }
        initializer {
            HomePerawatanViewModel(aplikasiKlinikGw().container.perawatanRepository)
        }
        initializer {
            InsertPsnViewModel(
                aplikasiKlinikGw().container.pasienRepository,
                aplikasiKlinikGw().container.jenisRepository
            )
        }
        initializer {
            InsertPrwtnViewModel(
                aplikasiKlinikGw().container.pasienRepository,
                aplikasiKlinikGw().container.dokterRepository,
                aplikasiKlinikGw().container.perawatanRepository
            )
        }
        initializer {
            InsertJnsViewModel(aplikasiKlinikGw().container.jenisRepository)
        }
        initializer {
            InsertDokterViewModel(aplikasiKlinikGw().container.dokterRepository)
        }
        initializer {
            DetailJenisViewModel(
                createSavedStateHandle(),
                aplikasiKlinikGw().container.jenisRepository
            )
        }
        initializer {
            DetailViewModel(
                createSavedStateHandle(),
                aplikasiKlinikGw().container.pasienRepository
            )
        }
        initializer {
            DetailDokterViewModel(
                createSavedStateHandle(),
                aplikasiKlinikGw().container.dokterRepository
            )
        }
        initializer {
            UpdateViewModel(
                createSavedStateHandle(),
                aplikasiKlinikGw().container.pasienRepository,
                aplikasiKlinikGw().container.jenisRepository
            )
        }
        initializer {
            UpdateJenisViewModel(
                createSavedStateHandle(),
                aplikasiKlinikGw().container.jenisRepository
            )
        }
        initializer {
            UpdateDokterViewModel(
                createSavedStateHandle(),
                aplikasiKlinikGw().container.dokterRepository
            )
        }
        initializer {
            UpdatePerawatanViewModel(
                createSavedStateHandle(),
                aplikasiKlinikGw().container.pasienRepository,
                aplikasiKlinikGw().container.perawatanRepository,
                aplikasiKlinikGw().container.dokterRepository
            )
        }
        initializer {
            DetailPerawatanViewModel(
                createSavedStateHandle(),
                aplikasiKlinikGw().container.perawatanRepository
            )
        }
    }
}


fun CreationExtras.aplikasiKlinikGw(): KlinikHewanApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KlinikHewanApplication)