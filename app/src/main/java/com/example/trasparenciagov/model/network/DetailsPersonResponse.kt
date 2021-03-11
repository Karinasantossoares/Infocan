package com.example.trasparenciagov.model.network

data class DetailsPersonResponse(
    val nome: String,
    val email: String,
    val dataNascimento: String,
    val siglaPartido: String,
    val telefone: String,
    val situacao: String,
    val urlFoto:String
)

