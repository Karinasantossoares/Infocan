package com.example.trasparenciagov.model.dto

data class DetailsPersonResponseDTO(
    val dados: Dados
)

data class Dados(
    val dataNascimento: String,
    val ultimoStatus: Status
)

data class Status(
    val nome: String,
    val email: String,
    val urlFoto: String,
    val siglaPartido: String,
    val gabinete: Gabinete,
    val situacao: String
)

data class Gabinete(
    val telefone: String
)

