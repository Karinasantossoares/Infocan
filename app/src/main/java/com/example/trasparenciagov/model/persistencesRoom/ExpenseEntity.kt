package com.example.trasparenciagov.model.persistencesRoom

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trasparenciagov.model.network.DespesasResponse

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) var uid: Int?=0,
    val tipoDespesa: String,
    val valorDocumento: Float,
    val dataDocumento: String
){
    fun toExpenseResponse() =
        DespesasResponse(
            tipoDespesa,
            valorDocumento,
            dataDocumento
        )
}