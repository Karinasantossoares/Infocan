package com.example.trasparenciagov.model.persistencesRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey var uid: Int= 0,
    val tipoDespesa: String,
    val valorDocumento: Float,
    val dataDocumento: String
)