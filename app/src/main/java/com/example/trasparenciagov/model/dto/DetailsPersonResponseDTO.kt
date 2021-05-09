package com.example.trasparenciagov.model.dto

import com.example.trasparenciagov.model.network.DetailsPersonResponse

data class DetailsPersonResponseDTO(
    val dados: Dados
){
    fun toDetailsResponse() :DetailsPersonResponse =
        DetailsPersonResponse(
            this.dados.ultimoStatus.email,
            this.dados.dataNascimento,
            this.dados.ultimoStatus.gabinete.telefone,
            this.dados.ultimoStatus.situacao
        )
}

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

