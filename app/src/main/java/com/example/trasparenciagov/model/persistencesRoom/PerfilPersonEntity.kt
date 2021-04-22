package com.example.trasparenciagov.model.persistencesRoom

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trasparenciagov.model.network.DetailsPersonResponse

@Entity(tableName = "perfil")
data class PerfilPersonEntity (
    @PrimaryKey(autoGenerate = true)
    val uid :Int =0,
    val nome: String,
    val urlFoto: String,
    val siglaPartido: String,
    @Embedded
    val detail: DetailsPersonResponse? = null
)