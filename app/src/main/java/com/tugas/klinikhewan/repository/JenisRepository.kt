package com.tugas.klinikhewan.repository

import com.tugas.klinikhewan.model.AllJenisResponse
import com.tugas.klinikhewan.model.Jenis
import com.tugas.klinikhewan.service.JenisService
import java.io.IOException

interface JenisRepository{
    suspend fun insertJenis(jenis: Jenis)

    suspend fun getAllJenis(): AllJenisResponse

    suspend fun getJenisById(idjenishewan: String): Jenis

    suspend fun updateJenis(idjenishewan: String, jenis: Jenis)

    suspend fun deleteJenis(idjenishewan: String)

}

class NetworkJenisRepository(
    private val jenisApiService: JenisService
) : JenisRepository {
    override suspend fun insertJenis(jenis: Jenis) {
        jenisApiService.insertJenis(jenis)
    }
    override suspend fun getAllJenis(): AllJenisResponse {
        return jenisApiService.getAllJenis()
    }
    override suspend fun getJenisById(idjenishewan: String): Jenis {
        return jenisApiService.getJenisById(idjenishewan).data
    }
    override suspend fun updateJenis(idjenishewan: String, jenis: Jenis) {
        jenisApiService.updateJenis(idjenishewan, jenis)
    }
    override suspend fun deleteJenis(idjenishewan: String) {
        try {
            val response = jenisApiService.deleteJenis(idjenishewan)
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