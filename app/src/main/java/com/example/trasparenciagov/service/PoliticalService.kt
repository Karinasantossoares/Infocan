package com.example.trasparenciagov.service

import com.example.trasparenciagov.model.dto.DespesasResponseDTO
import com.example.trasparenciagov.model.dto.DetailsPersonResponseDTO
import com.example.trasparenciagov.model.dto.PerfilPersonResponseDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PoliticalService {


    @GET("deputados/")
    fun getListPolitical(
        @Query("siglaUf") siglaUf: List<String>,
        @Query("pagina") numberPage: Int ,
        @Query("itens") numberItem: Int = 9 ):Single<PerfilPersonResponseDTO>

    @GET("deputados/{id}")
    fun getDetailsPolitical(@Path("id") id: Int): Single<DetailsPersonResponseDTO>

    @GET("deputados/{id}/despesas")
    fun getDetailsExpenses(@Path("id") id: Int): Single<DespesasResponseDTO>

    @GET("deputados")
    fun numberPage(@Query("pagina") totalPage: Int)
}