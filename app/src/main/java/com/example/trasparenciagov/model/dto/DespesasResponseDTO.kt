package com.example.trasparenciagov.model.dto

import com.example.trasparenciagov.model.network.DespesasResponse


data class DespesasResponseDTO(
    val dados: List<Details>
){
    fun toDespesasResponse()  =
        dados.map {
            DespesasResponse(
                it.tipoDespesa,
                it.valorDocumento,
                it.dataDocumento)
        }


}

data class Details(
    val tipoDespesa: String,
    val valorDocumento: Float,
    val dataDocumento: String
)