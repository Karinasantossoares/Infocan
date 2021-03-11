package com.example.trasparenciagov.repository.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trasparenciagov.model.persistencesRoom.ExpenseEntity

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    fun getExpense () : ExpenseEntity

    @Delete
    fun deleteExpense(expenseEntity: ExpenseEntity)

    @Insert
    fun insertExpense(expenseEntity: ExpenseEntity)
}