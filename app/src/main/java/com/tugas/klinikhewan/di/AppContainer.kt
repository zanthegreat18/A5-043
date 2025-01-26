package com.tugas.klinikhewan.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tugas.klinikhewan.repository.DokterRepository
import com.tugas.klinikhewan.repository.JenisRepository
import com.tugas.klinikhewan.repository.NetworkDokterRepository
import com.tugas.klinikhewan.repository.NetworkJenisRepository
import com.tugas.klinikhewan.repository.NetworkKontakRepository
import com.tugas.klinikhewan.repository.NetworkPerawatanRepository
import com.tugas.klinikhewan.repository.PasienRepository
import com.tugas.klinikhewan.repository.PerawatanRepository
import com.tugas.klinikhewan.service.DokterService
import com.tugas.klinikhewan.service.JenisService
import com.tugas.klinikhewan.service.PasienService
import com.tugas.klinikhewan.service.PerawatanService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val pasienRepository: PasienRepository
    val jenisRepository: JenisRepository
    val dokterRepository: DokterRepository
    val perawatanRepository: PerawatanRepository
}

class KlinikHewanContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:4000/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val pasienService: PasienService by lazy {
        retrofit.create(PasienService::class.java)
    }

    private val jenisService: JenisService by lazy {
        retrofit.create(JenisService::class.java)
    }

    private val dokterService: DokterService by lazy {
        retrofit.create(DokterService::class.java)
    }

    private val perawatanService: PerawatanService by lazy {
        retrofit.create(PerawatanService::class.java)
    }

    override val pasienRepository: PasienRepository by lazy {
        NetworkKontakRepository(pasienService)
    }
    override val jenisRepository: JenisRepository by lazy {
        NetworkJenisRepository(jenisService)
    }
    override val dokterRepository: DokterRepository by lazy {
        NetworkDokterRepository(dokterService)
    }
    override val perawatanRepository: PerawatanRepository by lazy {
        NetworkPerawatanRepository(perawatanService)
    }
}

