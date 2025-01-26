package com.tugas.klinikhewan.service

import com.tugas.klinikhewan.model.AllJenisResponse
import com.tugas.klinikhewan.model.Jenis
import com.tugas.klinikhewan.model.JenisDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JenisService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("jenis/store")
    suspend fun insertJenis(@Body jenis: Jenis)

    @GET("jenis/.")
    suspend fun getAllJenis(): AllJenisResponse

    @GET("jenis/{id_jenis_hewan}")
    suspend fun getJenisById(@Path("id_jenis_hewan") idjenishewan: String): JenisDetailResponse

    @PUT("jenis/{id_jenis_hewan}")
    suspend fun updateJenis(@Path("id_jenis_hewan") idjenishewan: String, @Body jenis: Jenis)

    @DELETE("jenis/{id_jenis_hewan}")
    suspend fun deleteJenis(@Path("id_jenis_hewan") idjenishewan: String): Response<Void>

}