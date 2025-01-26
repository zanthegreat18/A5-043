package com.tugas.klinikhewan.repository

import com.tugas.klinikhewan.model.AllDokterResponse
import com.tugas.klinikhewan.model.Dokter
import com.tugas.klinikhewan.service.DokterService
import java.io.IOException

interface DokterRepository{
    suspend fun insertDokter(dokter: Dokter)

    suspend fun getAllDokter(): AllDokterResponse

    suspend fun updateDokter(iddokter: String, dokter: Dokter)

    suspend fun deleteDokter(iddokter: String)

    suspend fun getDokterById(iddokter: String): Dokter
}

class NetworkDokterRepository(
    private val dokterApiService: DokterService
) : DokterRepository {
    override suspend fun insertDokter(dokter: Dokter) {
        dokterApiService.insertDokter(dokter)
    }
    override suspend fun getAllDokter(): AllDokterResponse {
        return dokterApiService.getAllDokter()
    }
    override suspend fun getDokterById(iddokter: String): Dokter {
        return dokterApiService.getDokterById(iddokter).data
    }
    override suspend fun updateDokter(iddokter: String, dokter: Dokter) {
        dokterApiService.updateDokter(iddokter, dokter)
    }
    override suspend fun deleteDokter(iddokter: String) {
        try {
            val response = dokterApiService.deleteDokter(iddokter)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete jenis. HTTP Status Code:"+
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

}