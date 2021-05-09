package com.example.trasparenciagov.model.dto

import com.example.trasparenciagov.model.network.PerfilPersonResponse

data class PerfilPersonResponseDTO(
    val dados: List<DetailsDateCongressManDTO>
){
    fun toPerfilPerson(): List<PerfilPersonResponse> = dados.map {
        PerfilPersonResponse(
            it.id,
            it.nome,
            it.urlFoto,
            it.siglaPartido
        )
    }
}


data class DetailsDateCongressManDTO(
    val id: Int,
    val nome: String,
    val urlFoto: String,
    val siglaPartido: String)

