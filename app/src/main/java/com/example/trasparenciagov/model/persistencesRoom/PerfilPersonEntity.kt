package com.example.trasparenciagov.model.persistencesRoom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perfil")
data class PerfilPersonEntity (
    @PrimaryKey(autoGenerate = true)val uid :Int =0,
    val nome: String,
    val urlFoto: String,
    val siglaPartido: String

)