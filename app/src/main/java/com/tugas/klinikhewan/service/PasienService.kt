package com.tugas.klinikhewan.service

import com.tugas.klinikhewan.model.AllPasienResponse
import com.tugas.klinikhewan.model.Pasien
import com.tugas.klinikhewan.model.PasienDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PasienService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("pasien/store")
    suspend fun insertPasien(@Body pasien: Pasien)

    @GET("pasien/.")
    suspend fun getAllPasien(): AllPasienResponse

    @GET("pasien/{id_hewan}")
    suspend fun getPasienById(@Path("id_hewan") idhewan: String): PasienDetailResponse

    @PUT("pasien/{id_hewan}")
    suspend fun updatePasien(@Path("id_hewan") idhewan: String, @Body pasien: Pasien)

    @DELETE("pasien/{id_hewan}")
    suspend fun deletePasien(@Path("id_hewan") idhewan: String): Response<Void>

}