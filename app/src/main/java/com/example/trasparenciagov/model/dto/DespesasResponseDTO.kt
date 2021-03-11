package com.example.trasparenciagov.model.dto


data class DespesasResponseDTO(
    val dados: List<Details>
)

data class Details(
    val tipoDespesa: String,
    val valorDocumento: Float,
    val dataDocumento: String
)