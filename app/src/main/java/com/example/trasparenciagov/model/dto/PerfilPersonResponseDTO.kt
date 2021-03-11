package com.example.trasparenciagov.model.dto

data class PerfilPersonResponseDTO(
    val dados:List<DetailsDateCongressManDTO>
)

data class DetailsDateCongressManDTO(
    val id: Int,
    val nome: String,
    val urlFoto: String,
    val siglaPartido: String
)