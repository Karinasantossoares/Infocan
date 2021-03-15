package com.example.trasparenciagov.model.persistencesRoom

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trasparenciagov.model.dto.Gabinete
import com.example.trasparenciagov.model.dto.Status

@Entity (tableName = "details")
data class DetailsPersonEntity(
    @PrimaryKey(autoGenerate = true) var uid: Int? =0,
    val nome: String,
    val email: String,
    val urlFoto: String,
    val siglaPartido: String,
    val situacao: String,
    val dataNascimento: String,
    val telefone: String
)